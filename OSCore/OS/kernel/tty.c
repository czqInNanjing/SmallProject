
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                               tty.c
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                                    Forrest Yu, 2005
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

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

#define TTY_FIRST	(tty_table)
#define TTY_END		(tty_table + NR_CONSOLES)

PRIVATE void init_tty(TTY* p_tty);
PRIVATE void tty_do_read(TTY* p_tty);
PRIVATE void tty_do_write(TTY* p_tty);
PRIVATE void put_key(TTY* p_tty, u32 key);
PRIVATE void dealwithESCMode(TTY* p_tty);
PRIVATE void finishInput(TTY* p_tty);

unsigned int tabs[100];
int tabs_len;
unsigned int tabSpace[100];
unsigned int backspace[100];
int backspace_len;
unsigned int backspace_pos[100];
int checkMode;
unsigned int storeCursorForESC;
extern int clearConsole; 
extern void searchAndChangColor(TTY* p_tty , int len);
extern void backColor(TTY* p_tty);
/*======================================================================*
                           task_tty
 *======================================================================*/
PUBLIC void task_tty()
{
	TTY*	p_tty;
	tabs_len = 0;
	backspace_len = 0;
	checkMode = 0;
	init_keyboard();
	clearConsole = 1;
	for (p_tty=TTY_FIRST;p_tty<TTY_END;p_tty++) {
		init_tty(p_tty);
	}
	select_console(0);
	while (1) {
		for (p_tty=TTY_FIRST;p_tty<TTY_END;p_tty++ ) {
					if (clearConsole == 1 && is_current_console(p_tty->p_console) )
                	 {
                	 clearConsole = 0;
			 
                     	while(p_tty->p_console->cursor > 0  && checkMode == 0){
                     		out_char(p_tty->p_console,'\b');
                   	  	
                   		}
		       		
                        
			 }


			tty_do_read(p_tty);
			tty_do_write(p_tty);
		}
	}
}

/*======================================================================*
			   init_tty
 *======================================================================*/
PRIVATE void init_tty(TTY* p_tty)
{
	p_tty->inbuf_count = 0;
	p_tty->p_inbuf_head = p_tty->p_inbuf_tail = p_tty->in_buf;

	init_screen(p_tty);
}

/*======================================================================*
				in_process
 *======================================================================*/
PUBLIC void in_process(TTY* p_tty, u32 key)
{
        char output[2] = {'\0', '\0'};
        int space;
        if (!(key & FLAG_EXT)) {  //若不是特殊字符
			put_key(p_tty, key);
        }
        else {
                int raw_code = key & MASK_RAW;
                switch(raw_code) {
                case ENTER:
                if(checkMode == 1){
                	finishInput(p_tty);
                }else{
                	put_key(p_tty, '\n');
                }
				
				break;

                case BACKSPACE:
				put_key(p_tty, '\b');
				break;

				case TAB:
				
				space = p_tty->p_console->cursor%SCREEN_WIDTH;
				if(SCREEN_WIDTH - space > 4){
					space = 4- space%4;
				}else{
					space = SCREEN_WIDTH - space;
				}
				for (int i = 0; i < space; ++i)
				{
					put_key(p_tty , ' ');
				}
				tabSpace[tabs_len] = space;
                tabs[tabs_len++]= p_tty->p_console->cursor;
				break;

				case ESC:
				if(checkMode == 0){
					dealwithESCMode(p_tty);
				} else if(checkMode == 2){
					checkMode = 0;
					backColor(p_tty);
				}
				
				break;
                case UP:
                        if ((key & FLAG_SHIFT_L) || (key & FLAG_SHIFT_R)) {
				scroll_screen(p_tty->p_console, SCR_DN);
                        }
				break;
				case DOWN:
				if ((key & FLAG_SHIFT_L) || (key & FLAG_SHIFT_R)) {
				scroll_screen(p_tty->p_console, SCR_UP);
				}
				break;
		case F1:
		case F2:
		case F3:
		case F4:
		case F5:
		case F6:
		case F7:
		case F8:
		case F9:
		case F10:
		case F11:
		case F12:
			/* Alt + F1~F12 */
			if ((key & FLAG_ALT_L) || (key & FLAG_ALT_R)) {
				select_console(raw_code - F1);
			}
			break;
                default:
                        break;
                }
        }
}

/*======================================================================*
			      put_key
*======================================================================*/
PRIVATE void put_key(TTY* p_tty, u32 key)
{
	if (p_tty->inbuf_count < TTY_IN_BYTES) {
		*(p_tty->p_inbuf_head) = key;
		p_tty->p_inbuf_head++;
		if (p_tty->p_inbuf_head == p_tty->in_buf + TTY_IN_BYTES) {
			p_tty->p_inbuf_head = p_tty->in_buf;
		}
		p_tty->inbuf_count++;
	}
}


/*======================================================================*
			      tty_do_read
 *======================================================================*/
PRIVATE void tty_do_read(TTY* p_tty)
{
	if (is_current_console(p_tty->p_console)) {
		keyboard_read(p_tty);
	}
}


/*======================================================================*
			      tty_do_write
 *======================================================================*/
PRIVATE void tty_do_write(TTY* p_tty)
{
	if (p_tty->inbuf_count) {
		char ch = *(p_tty->p_inbuf_tail);
		p_tty->p_inbuf_tail++;
		if (p_tty->p_inbuf_tail == p_tty->in_buf + TTY_IN_BYTES) {
			p_tty->p_inbuf_tail = p_tty->in_buf;
		}
		p_tty->inbuf_count--;

		out_char(p_tty->p_console, ch);
	}
}

PRIVATE void dealwithESCMode(TTY* p_tty){
	checkMode = 1;
	storeCursorForESC = p_tty->p_console->cursor;
}
PRIVATE void finishInput(TTY* p_tty){
	checkMode = 2;

	int len = p_tty->p_console->cursor - storeCursorForESC;
	searchAndChangColor(p_tty , len);


}
