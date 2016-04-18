package edu.nju.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.nju.model.po.BlockPO;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.model.state.BlockState;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.BlockVO;
import edu.nju.network.Configure;

/**
 * 游戏模型核心�?
 * 
 * @author czq
 *
 */
public class ChessBoardModelImpl extends BaseModel implements
		ChessBoardModelService {

	private GameModelService gameModel;
	private ParameterModelImpl parameterModel;

	private BlockPO[][] blockMatrix;
	private int height;
	private int width;

	public ChessBoardModelImpl(ParameterModelImpl parameterModel) {
		this.parameterModel = parameterModel;
	}

	@Override
	public boolean initialize(int width, int height, int mineNum) {
		blockMatrix = new BlockPO[width][height];
		this.height = height;
		this.width = width;
		setBlock(mineNum);

		this.parameterModel.setMineNum(mineNum);
		this.parameterModel.clearFlag();

		// this.printBlockMatrix();

		return false;
	}

	@Override
	public boolean excavate(int x, int y) {
		if (blockMatrix == null) {
			return false;
		}
		
		
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];

		if (block.getState() != BlockState.UNCLICK) {
			return true;
		}
		block.setState(BlockState.CLICK);
		blocks.add(block);
		if(Configure.isNetWork){
			if(block.isMine()){
				super.updateChange(new UpdateMessage("excute", this.getDisplayList(
						blocks, GameState.OVER)));
				if(this.isFromClient){
					this.gameModel.gameOver(GameResultState.HOSTWIN);
				}else{
					this.gameModel.gameOver(GameResultState.CLIENTWIN);
				}
			}
		}
		GameState gameState = GameState.RUN;
		if (block.isMine()) {
			gameState = GameState.OVER;

			// 先让他知道自己是怎么死的，再来标�?
			for (int i = 0; i < blockMatrix.length; i++) {
				for (int j = 0; j < blockMatrix[0].length; j++) {
					if (blockMatrix[i][j].getState() == BlockState.FLAG
							|| blockMatrix[i][j].isMine()) {
						blocks.add(blockMatrix[i][j]);
					}
				}
			}

			super.updateChange(new UpdateMessage("excute", this.getDisplayList(
					blocks, gameState)));
			this.gameModel.gameOver(GameResultState.FAIL);
			return true;
		} else if (block.getMineNum() == 0) {
			excavatEmptyBlocks(block, blocks);
		}
		if (gameState == GameState.RUN) {
			boolean isWin = true;
			for (int i = 0; i < blockMatrix.length; i++) {
				for (int j = 0; j < blockMatrix[0].length; j++) {
					if ((!blockMatrix[i][j].isMine())
							&& blockMatrix[i][j].getState() == BlockState.UNCLICK) {
						isWin = false;
					}
				}
			}
			if (isWin) {
				super.updateChange(new UpdateMessage("excute", this.getDisplayList(
						blocks, gameState)));
				gameState = GameState.OVER;
				this.gameModel.gameOver(GameResultState.SUCCESS);
				for (int i = 0; i < blockMatrix.length; i++) {
					for (int j = 0; j < blockMatrix[0].length; j++) {
						if (blockMatrix[i][j].getState() == BlockState.UNCLICK) {
							blockMatrix[i][j].setState(BlockState.FLAG);
							blocks.add(blockMatrix[i][j]);
						}
					}
				}
				return true;
			}
		}

		super.updateChange(new UpdateMessage("excute", this.getDisplayList(
				blocks, gameState)));
		return true;
	}

	@Override
	public boolean mark(int x, int y) {
		if (blockMatrix == null)
			return false;

		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		BlockState state = block.getState();
		GameResultState gameResult = GameResultState.SUCCESS;

		if (Configure.isNetWork) {
			GameState gameState = GameState.RUN;
			if (state == BlockState.UNCLICK) {

				if (!block.isMine()) {
					gameState = GameState.OVER;
					if (this.isFromClient) {
						gameResult = GameResultState.HOSTWIN;
					} else {
						gameResult = GameResultState.CLIENTWIN;
					}
					for (int i = 0; i < blockMatrix.length; i++) {
						for (int j = 0; j < blockMatrix[0].length; j++) {
							if (blockMatrix[i][j].isMine()) {
								blocks.add(blockMatrix[i][j]);
							}
						}
					}
				} else {
					if (this.isFromClient) {
						block.setState(BlockState.CLIENT_FLAG);
						this.parameterModel.addClientFlag();
					} else {
						block.setState(BlockState.HOST_FLAG);
						this.parameterModel.addHostFlag();
					}
					this.parameterModel.minusMineNum();
					if (this.parameterModel.getMineNum() == 0) {
						gameState = GameState.OVER;
						if (parameterModel.getClientFlag() > parameterModel.getHostFlag()) {
							gameResult = GameResultState.CLIENTWIN;
						} else if (parameterModel.getClientFlag() < parameterModel.getHostFlag()) {
							gameResult = GameResultState.HOSTWIN;
						} else {
							gameResult = GameResultState.DRAWEND;
						}
					} else {
						gameState = GameState.RUN;
					}
					blocks.add(block);
					super.updateChange(new UpdateMessage("excute", this
							.getDisplayList(blocks, gameState)));
//					super.updateChange(new UpdateMessage("flagChange", this
//							.flagToVo(clientFlag, hostFlag)));
				}
				if (gameState == GameState.OVER) {
						this.gameModel.gameOver(gameResult);
				}
				return true;
			} else {
				return true;
			}

		}
		// 以上为网络部分

		if (state == BlockState.UNCLICK) {
			if (this.parameterModel.minusMineNum()) {
				block.setState(BlockState.FLAG);
			}

		} else if (state == BlockState.FLAG) {
			block.setState(BlockState.UNCLICK);
			this.parameterModel.addMineNum();
		}

		blocks.add(block);
		super.updateChange(new UpdateMessage("excute", this.getDisplayList(
				blocks, GameState.RUN)));

		return true;
	}



	@Override
	public boolean quickExcavate(int x, int y) {
		if (blockMatrix == null) {
			return false;
		}
		BlockPO block = blockMatrix[x][y];
		if (block.getState() == BlockState.CLICK) {
			List<BlockPO> nearBy = getNearbyBlock(block);
			int flagNum = 0;
			
			if(Configure.isNetWork){
				for (BlockPO blockPO : nearBy) {
					if (blockPO.getState() == BlockState.CLIENT_FLAG || blockPO.getState() == BlockState.HOST_FLAG) {
						flagNum++;
					}
				}
				if (block.getMineNum() != 0 && block.getMineNum() == flagNum) {
					for (BlockPO blockPO : nearBy) {
						if (blockPO.getState() == BlockState.UNCLICK) {
							excavate(blockPO.getX(), blockPO.getY());
						}
					}
				}
				return true;
			}
			
			
			for (BlockPO blockPO : nearBy) {
				if (blockPO.getState() == BlockState.FLAG) {
					flagNum++;
				}
			}
			if (block.getMineNum() != 0 && block.getMineNum() == flagNum) {
				for (BlockPO blockPO : nearBy) {
					if (blockPO.getState() == BlockState.UNCLICK) {
						excavate(blockPO.getX(), blockPO.getY());
					}
				}
			}

		} else {
			return true;
		}

		return false;
	}

	/**
	 * 初始化BlockMatrix中的Block，并随机设置mineNum颗雷 同时可以为每个Block设定附近的雷�?
	 * 
	 * @param mineNum
	 * @return
	 */
	private boolean setBlock(int mineNum) {
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		Random rand = new Random();

		int[] mineOperation = new int[mineNum];
		for (int i = 0; i < mineOperation.length; i++) {
			mineOperation[i] = rand.nextInt(width * height);
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				blockMatrix[i][j] = new BlockPO(i, j);
			}
		}
		while (mineNum > 0) {
			int x, y;
			int randInt = rand.nextInt(width * height) + 1;
			if (randInt % width == 0) {
				x = width - 1;
				y = randInt / width - 1;
			} else {
				x = randInt % width - 1;
				y = randInt / width;
			}
			if (blockMatrix[x][y].isMine()) {
				continue;
			}
			blockMatrix[x][y].setMine(true);

			mineNum--;
			addMineNum(x, y);
		}

		return false;
	}

	/**
	 * 挖开空的直至到达边缘和数量
	 * 
	 * @param block
	 * @param blocks
	 */
	private void excavatEmptyBlocks(BlockPO block, List<BlockPO> blocks) {
		for (BlockPO blockPO : getNearbyBlock(block)) {
			if (blockPO.getState() == BlockState.UNCLICK) {
				blockPO.setState(BlockState.CLICK);
				blocks.add(blockPO);
				if (blockPO.getMineNum() == 0) {
					excavatEmptyBlocks(blockPO, blocks);
				}
			}
		}
	}

	/**
	 * 
	 * 把(i,j)位置附近的Block雷数+1
	 * 
	 * @param i
	 * @param j
	 */
	private void addMineNum(int i, int j) {
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;

		int tempI = i - 1;

		for (; tempI <= i + 1; tempI++) {
			int tempJ = j - 1;
			for (; tempJ <= j + 1; tempJ++) {
				if ((tempI > -1 && tempI < width)
						&& (tempJ > -1 && tempJ < height)) {
					blockMatrix[tempI][tempJ].addMine();
				}
			}
		}

	}

	/**
	 * 将逻辑对象转化为显示对象
	 * 
	 * @param blocks
	 * @param gameState
	 * @return
	 */
	private List<BlockVO> getDisplayList(List<BlockPO> blocks,
			GameState gameState) {
		List<BlockVO> result = new ArrayList<BlockVO>();
		for (BlockPO block : blocks) {
			if (block != null) {
				BlockVO displayBlock = block.getDisplayBlock(gameState);
				if (displayBlock.getState() != null)
					result.add(displayBlock);
			}
		}
		return result;
	}

	@Override
	public void setGameModel(GameModelService gameModel) {
		this.gameModel = gameModel;
	}

	/**
	 * 获得附近八个雷，如果是边缘会减少
	 * 
	 * @param block
	 * @return
	 */
	private List<BlockPO> getNearbyBlock(BlockPO block) {
		List<BlockPO> blocks = new ArrayList<>();
		int x = block.getX();
		int y = block.getY();
		if (x - 1 >= 0) {
			blocks.add(blockMatrix[x - 1][y]);
			if (y + 1 < height) {
				blocks.add(blockMatrix[x - 1][y + 1]);
			}
			if (y - 1 >= 0) {
				blocks.add(blockMatrix[x - 1][y - 1]);
			}
		}
		if (y + 1 < height) {
			blocks.add(blockMatrix[x][y + 1]);
		}
		if (y - 1 >= 0) {
			blocks.add(blockMatrix[x][y - 1]);
		}
		if (x + 1 < width) {
			blocks.add(blockMatrix[x + 1][y]);
			if (y + 1 < height) {
				blocks.add(blockMatrix[x + 1][y + 1]);
			}
			if (y - 1 >= 0) {
				blocks.add(blockMatrix[x + 1][y - 1]);
			}
		}

		return blocks;
	}

	// /**
	// * 测试用方�?
	// */
	// private void printBlockMatrix(){
	// for(BlockPO[] blocks : this.blockMatrix){
	// for(BlockPO b :blocks){
	// String p = b.getMineNum()+"";
	// if(b.isMine())
	// p="*";
	// System.out.print(p);
	// }
	// System.out.println();
	// }
	// }
	public ParameterModelImpl getParameterModel() {
		return parameterModel;
	}
}
