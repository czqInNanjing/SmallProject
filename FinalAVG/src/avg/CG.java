package avg;

import java.awt.Image;
import java.io.Serializable;

import collection.ArrayMap;
import resource.CacheResource;

/**
 * 将Chara给“包装”起来，然后给予AVGScript去画
 * @author czq
 *
 */
public class CG {

	
	/**
	 * 游戏背景图
	 */
	private Image backgroundCG;

	public Image getBackgroundCG() {
		return backgroundCG;
	}

	public void setBackgroundCG(Image backgroundCG) {
		this.backgroundCG = backgroundCG;
	}
	/**
	 * 增加cg到人物列表中
	 * @param file
	 * @param role
	 */
	public void addChara(String file, Chara role) {
		CacheResource.ADV_CHARAS.put(file.replaceAll(" ", "").toLowerCase(),
				role);
	}
	
	public void addImage(String file, int x, int y) {
		String keyName = file.replaceAll(" ", "").toLowerCase();
		Chara chara = (Chara) CacheResource.ADV_CHARAS.get(keyName);
		if (chara == null) {
			CacheResource.ADV_CHARAS.put(keyName, new Chara(file, x, y));
		} else {
			chara.setX(x);
			chara.setY(y);
		}
	}

	public Chara removeImage(String file) {
		return (Chara) CacheResource.ADV_CHARAS.remove(file);
	}

	public void clear() {
		CacheResource.ADV_CHARAS.clear();
	}

	public ArrayMap getCharas() {
		return CacheResource.ADV_CHARAS;
	}

}
