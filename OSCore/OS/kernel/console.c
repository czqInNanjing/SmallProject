
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			      console.c
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
						    Forrest Yu, 2005
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/


/*
	回车键: 把光标移到第一列
	换行键: 把光标前进到下一行
*/


#include "type.h"
#include "const.h"
#include "protect.h"
#include "string.h"
#include "proc.h"
#include "tty.h"
#include "console.h"
#include "global.h"
#include "keyboard.h"
#include "proto.h"

PRIVATE void set_cursor(unsigned int position);
PRIVATE void set_video_start_addr(u32 addr);
PRIVATE void flush(CONSOLE* p_con);

extern unsigned int tabs[100];
extern int tabs_len;
extern unsigned int tabSpace[100];
extern unsigned int backspace[100];
extern int backspace_len;
extern unsigned int backspace_pos[100];
extern int checkMode;
extern unsigned int storeCursorForESC;
void searchAndChangColor(TTY* p_tty, int len);
void backColor(TTY* p_tty);
/*======================================================================*
			   clear_screen
 *======================================================================*/

PUBLIC void clear_screen(CONSOLE * p_con)
{
 while (p_con -> cursor != 1)
 {
   out_char(p_con,'\b');
 }
}
/*======================================================================*
			   init_screen
 *======================================================================*/
PUBLIC void init_screen(TTY* p_tty)
{
	int nr_tty = p_tty - tty_table;
	p_tty->p_console = console_table + nr_tty;

	int v_mem_size = V_MEM_SIZE >> 1;	/* 显存总大小 (in WORD) */

	int con_v_mem_size                   = v_mem_size / NR_CONSOLES;
	p_tty->p_console->original_addr      = nr_tty * con_v_mem_size;
	p_tty->p_console->v_mem_limit        = con_v_mem_size;
	p_tty->p_console->current_start_addr = p_tty->p_console->original_addr;

	/* 默认光标位置在最开始处 */
	p_tty->p_console->cursor = p_tty->p_console->original_addr;

	if (nr_tty == 0) {
		/* 第一个控制台沿用原来的光标位置 */
		p_tty->p_console->cursor = disp_pos / 2;
		disp_pos = 0;
	}
	else {
		out_char(p_tty->p_console, nr_tty + '0');
		out_char(p_tty->p_console, '#');
	}

	set_cursor(p_tty->p_console->cursor);
}


/*======================================================================*
			   is_current_console
*======================================================================*/
PUBLIC int is_current_console(CONSOLE* p_con)
{
	return (p_con == &console_table[nr_current_console]);
}


/*======================================================================*
			   out_char
 *======================================================================*/
PUBLIC void out_char(CONSOLE* p_con, char ch)
{
	u8* p_vmem = (u8*)(V_MEM_BASE + p_con->cursor * 2);
        char ch_color =  DEFAULT_CHAR_COLOR;
	switch(ch) {
	case '\n':
		if (p_con->cursor < p_con->original_addr +
		    p_con->v_mem_limit - SCREEN_WIDTH) {

			unsigned int temp = p_con->cursor;

			p_con->cursor = p_con->original_addr + SCREEN_WIDTH * 
				((p_con->cursor - p_con->original_addr) /
				 SCREEN_WIDTH + 1);

			backspace_pos[backspace_len] = p_con->cursor;
			backspace[backspace_len] = p_con->cursor - temp;
			backspace_len++;

		}
		break;
	case '\b':
		if (p_con->cursor > p_con->original_addr) {
					// out_char(p_con , tabSpace[tabs_len - 1] + '0');
					// out_char(p_con , tabs[tabs_len -1  ] + '0');
                 if (tabs_len > 0 && p_con->cursor == tabs[tabs_len - 1]+tabSpace[tabs_len - 1]){
                   
                   p_con->cursor -= tabSpace[tabs_len - 1];
                   tabs_len--;
            }else if (backspace_len > 0 && p_con->cursor == backspace_pos[backspace_len - 1])
            {
            	p_con->cursor -= backspace[backspace_len - 1];
             		backspace_len--;
            }else{
            	p_con->cursor--;
				*(p_vmem-2) = ' ';
				*(p_vmem-1) = DEFAULT_CHAR_COLOR;
            }	
		   }

		break;
	default:
		if (p_con->cursor <
		    p_con->original_addr + p_con->v_mem_limit - 1) {
               
			*p_vmem++ = ch;
			if(checkMode){
				*p_vmem++ = GREEN;
			}else{
				*p_vmem++ = ch_color;
			}
			
			p_con->cursor++;
		}
		break;
	}

	while (p_con->cursor >= p_con->current_start_addr + SCREEN_SIZE) {
		scroll_screen(p_con, SCR_DN);
	}

	flush(p_con);
}

/*======================================================================*
                           flush
*======================================================================*/
PRIVATE void flush(CONSOLE* p_con)
{
        set_cursor(p_con->cursor);
        set_video_start_addr(p_con->current_start_addr);
}

/*======================================================================*
			    set_cursor
 *======================================================================*/
PRIVATE void set_cursor(unsigned int position)
{
	disable_int();
	out_byte(CRTC_ADDR_REG, CURSOR_H);
	out_byte(CRTC_DATA_REG, (position >> 8) & 0xFF);
	out_byte(CRTC_ADDR_REG, CURSOR_L);
	out_byte(CRTC_DATA_REG, position & 0xFF);
	enable_int();
}

/*======================================================================*
			  set_video_start_addr
 *======================================================================*/
PRIVATE void set_video_start_addr(u32 addr)
{
	disable_int();
	out_byte(CRTC_ADDR_REG, START_ADDR_H);
	out_byte(CRTC_DATA_REG, (addr >> 8) & 0xFF);
	out_byte(CRTC_ADDR_REG, START_ADDR_L);
	out_byte(CRTC_DATA_REG, addr & 0xFF);
	enable_int();
}



/*======================================================================*
			   select_console
 *======================================================================*/
PUBLIC void select_console(int nr_console)	/* 0 ~ (NR_CONSOLES - 1) */
{
	if ((nr_console < 0) || (nr_console >= NR_CONSOLES)) {
		return;
	}

	nr_current_console = nr_console;

	set_cursor(console_table[nr_console].cursor);
	set_video_start_addr(console_table[nr_console].current_start_addr);
}

/*======================================================================*
			   scroll_screen
 *----------------------------------------------------------------------*
 滚屏.
 *----------------------------------------------------------------------*
 direction:
	SCR_UP	: 向上滚屏
	SCR_DN	: 向下滚屏
	其它	: 不做处理
 *======================================================================*/
PUBLIC void scroll_screen(CONSOLE* p_con, int direction)
{
	if (direction == SCR_UP) {
		if (p_con->current_start_addr > p_con->original_addr) {
			p_con->current_start_addr -= SCREEN_WIDTH;
		}
	}
	else if (direction == SCR_DN) {
		if (p_con->current_start_addr + SCREEN_SIZE <
		    p_con->original_addr + p_con->v_mem_limit) {
			p_con->current_start_addr += SCREEN_WIDTH;
		}
	}
	else{
	}

	set_video_start_addr(p_con->current_start_addr);
	set_cursor(p_con->cursor);
}



void searchAndChangColor(TTY* p_tty , int len){
	

	unsigned int pointer = p_tty->p_console->original_addr;

	u8* p_vmem;  
 	unsigned int searchFor = storeCursorForESC;
 	// out_char(p_tty->p_console  ,len + '0');
	while(pointer != storeCursorForESC){
		p_vmem = (u8*)(V_MEM_BASE + pointer * 2);
		int hasFind = 1;
		for (int i = 0; i < len; ++i)
		{
			// out_char(p_tty->p_console  , *p_vmem);
			// out_char(p_tty->p_console  , *((u8*)(V_MEM_BASE + searchFor*2) ));
			if( *p_vmem != *((u8*)( V_MEM_BASE + searchFor*2) )){
				hasFind = 0;
				break;
			}
			searchFor++;
			p_vmem += 2;
		}
		if(hasFind){
			for (int i = 0; i < len; ++i)
			{
				p_vmem -= 2 ;
				*(p_vmem+1) = GREEN;
			}
			p_vmem += 2*len;
		}
		// out_char(p_tty->p_console  , hasFind + '0');
		searchFor = storeCursorForESC;
		pointer++;


	}


}


void backColor(TTY* p_tty){
	while(p_tty->p_console->cursor > storeCursorForESC){
		out_char(p_tty->p_console,'\b');
	}
	
	unsigned int pointer = p_tty->p_console->original_addr;
	u8* p_vmem; 
	while(pointer != storeCursorForESC){
		p_vmem = (u8*)(V_MEM_BASE + pointer * 2);
		*(p_vmem+1) = DEFAULT_CHAR_COLOR;
		pointer++;
	}


}