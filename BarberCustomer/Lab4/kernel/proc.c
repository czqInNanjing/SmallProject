
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                               proc.c
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                                    Forrest Yu, 2005
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

#include "type.h"
#include "const.h"
#include "protect.h"
#include "tty.h"
#include "console.h"
#include "string.h"
#include "proc.h"
#include "global.h"
#include "proto.h"

/*======================================================================*
                              schedule
 *======================================================================*/
PUBLIC void schedule()
{
	PROCESS* p;
	int	 greatest_ticks = 0;

	while (!greatest_ticks) {
		for (p = proc_table; p < proc_table+NR_TASKS+NR_PROCS; p++) {
			if (p->ticks > greatest_ticks && p->sleepTicks == 0) {
				greatest_ticks = p->ticks;
				p_proc_ready = p;
			}
		}

		if (!greatest_ticks) {
			for(p=proc_table;p<proc_table+NR_TASKS+NR_PROCS;p++) {
				p->ticks = p->priority;
			}
		}
	}
}

/*======================================================================*
                           sys_get_ticks
 *======================================================================*/
PUBLIC int sys_get_ticks()
{
	return ticks;
}
/*======================================================================*
                           sys_process_sleep

 *======================================================================*/
PUBLIC void sys_process_sleep(int unused, int milli_sec, PROCESS * p){
	p->sleepTicks = milli_sec*HZ/1000;
}
PUBLIC void sys_tem_p(int toPrint,  semaphore * s, PROCESS * p){
	s->value=s->value-1;
	if(s->value < 0){ //no more thing to deal with
		//barber now should stop himself or customer has to wait
		sys_process_sleep(0 , 100000 , p);
		
		s->wait[-s->value - 1] = p;
			
		if( toPrint == -1){
			printf("Barber is going to sleep\n");
		}else if( toPrint > 0){
			printf("customer%d is going to sleep\n" , toPrint);
		}



		schedule();
	}
}
PUBLIC void sys_tem_v(int unused,  semaphore * s, PROCESS * p){
	s->value=s->value+1;
	if(s->value <= 0){ //now barber is available or one customer come in
		//wake up any customers or barber
		s->wait[0]->sleepTicks = 0;
		for (int i = 0 ; i < -s->value ; i++){
			s->wait[i] = s->wait[i+1];
		}
	}
}

// procedure p(var s:samephore);
// {
// s.value=s.value-1;
// if (s.value<0) asleep(s.queue);
// }
// procedure v(var s:samephore);
// {
// s.value=s.value+1;
// if (s.value<=0) wakeup(s.queue);
// }
