
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            main.c
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
#include "proto.h"

#define TTY_FIRST	(tty_table)

semaphore barber;
semaphore customer;
semaphore mutex;
int clearConsole = 0;
int chair;
int customerID;
int barberTime = 60000;
int waiting = 0;                     // the chairs that is seated now
int customer1ComeTime = 50000;
int customer2ComeTime = 90000;
int customer3ComeTime = 110000;
int barberCustomerID;

/*======================================================================*
                            kernel_main
 *======================================================================*/
PUBLIC int kernel_main()
{
	disp_str("-----\"kernel_main\" begins-----\n");

	TASK*		p_task		= task_table;
	PROCESS*	p_proc		= proc_table;
	char*		p_task_stack	= task_stack + STACK_SIZE_TOTAL;
	u16		selector_ldt	= SELECTOR_LDT_FIRST;
	int i;
        u8              privilege;
        u8              rpl;
        int             eflags;
	for (i = 0; i < NR_TASKS+NR_PROCS; i++) {
                if (i < NR_TASKS) {     /* 任务 */
                        p_task    = task_table + i;
                        privilege = PRIVILEGE_TASK;
                        rpl       = RPL_TASK;
                        eflags    = 0x1202; /* IF=1, IOPL=1, bit 2 is always 1 */
                }
                else {                  /* 用户进程 */
                        p_task    = user_proc_table + (i - NR_TASKS);
                        privilege = PRIVILEGE_USER;
                        rpl       = RPL_USER;
                        eflags    = 0x202; /* IF=1, bit 2 is always 1 */
                }

		strcpy(p_proc->p_name, p_task->name);	// name of the process
		p_proc->pid = i;			// pid

		p_proc->ldt_sel = selector_ldt;

		memcpy(&p_proc->ldts[0], &gdt[SELECTOR_KERNEL_CS >> 3],
		       sizeof(DESCRIPTOR));
		p_proc->ldts[0].attr1 = DA_C | privilege << 5;
		memcpy(&p_proc->ldts[1], &gdt[SELECTOR_KERNEL_DS >> 3],
		       sizeof(DESCRIPTOR));
		p_proc->ldts[1].attr1 = DA_DRW | privilege << 5;
		p_proc->regs.cs	= (0 & SA_RPL_MASK & SA_TI_MASK) | SA_TIL | rpl;
		p_proc->regs.ds	= (8 & SA_RPL_MASK & SA_TI_MASK) | SA_TIL | rpl;
		p_proc->regs.es	= (8 & SA_RPL_MASK & SA_TI_MASK) | SA_TIL | rpl;
		p_proc->regs.fs	= (8 & SA_RPL_MASK & SA_TI_MASK) | SA_TIL | rpl;
		p_proc->regs.ss	= (8 & SA_RPL_MASK & SA_TI_MASK) | SA_TIL | rpl;
		p_proc->regs.gs	= (SELECTOR_KERNEL_GS & SA_RPL_MASK) | rpl;

		p_proc->regs.eip = (u32)p_task->initial_eip;
		p_proc->regs.esp = (u32)p_task_stack;
		p_proc->regs.eflags = eflags;

		p_proc->nr_tty = 0;

		p_task_stack -= p_task->stacksize;
		p_proc++;
		p_task++;
		selector_ldt += 1 << 3;
	}

	proc_table[0].ticks = proc_table[0].priority = 15;
	proc_table[1].ticks = proc_table[1].priority =  5;
	proc_table[2].ticks = proc_table[2].priority =  5;
	proc_table[3].ticks = proc_table[3].priority =  5;
	proc_table[4].ticks = proc_table[4].priority =  5;
	proc_table[5].ticks = proc_table[5].priority =  5;

	proc_table[0].sleepTicks = 0;
	proc_table[1].sleepTicks = 0;//TESTA
	proc_table[2].sleepTicks = 0;//barber
	proc_table[3].sleepTicks = 0;//customer1
	proc_table[4].sleepTicks = 0;//c2
	proc_table[5].sleepTicks = 0;//c3
	

        // proc_table[1].nr_tty = 0;
        // proc_table[2].nr_tty = 0;
        // proc_table[3].nr_tty = 0;
	

	// disp_pos = 0;
	// for (int i = 0; i < 80*25; ++i)
	// {
	// 	disp_str(' ');
	// }
	// disp_pos = 0;

	clearConsole = 1;
	barber.value = 0 ;
	customer.value = 0 ;
	mutex.value = 1;
	customerID = 0;
	barberCustomerID = 0;
	waiting = 0;
	chair = 2;

	k_reenter = 0;
	ticks = 0;

	p_proc_ready	= proc_table;

	init_clock();
    init_keyboard();

	restart();

	while(1){}
}

/*======================================================================*
                               TestA
 *======================================================================*/
void TestA()
{
	int i = 0;
	while (1) {
		
		
		milli_delay(200000);

		 
	}



}

/*======================================================================*
                               Barber
 *======================================================================*/
void Barber()
{
	int i = 1;
	int nowBarberID;
	while(1){
		// printf("B %x", get_ticks());
		// milli_delay(20000);
		// sys_process_sleep(0,0,200000,&proc_table[2]);

	 	tem_p(-1,  &customer,&proc_table[i+1]) ;  
	 	tem_p(0 , &mutex , &proc_table[i+1]);                                           // in the critical section
	 // printf("now waiting customer num is %d\n", customer.value);	
	 	waiting--;
	 	
	 	tem_v(0 , &barber , &proc_table[i+1] ) ;

	 	tem_v(0 , &mutex , &proc_table[i+1]);
	 	nowBarberID = barberCustomerID;
	 	printf("start barbering for customer\n");
      	milli_delay(barberTime);
      	printf("barbering over , customer leaves\n");
      	
     	// sys_tem_v(0 , 0 , &mutex , &proc_table[2] ) ;  
     	

     	if(customerID%10 == 0){

     		  clearConsole = 1;
	 		
     
		}
     	}
}

/*======================================================================*
                               Customer1
 *======================================================================*/
void Customer1()
{
	int i = 2;
	int tempCustomerID;
	while(1){
		milli_delay(customer2ComeTime);                                // the next time before the customer come


		tem_p(0 , &mutex , &proc_table[i+1]);                         //go in the critical section
		tempCustomerID = ++customerID;                                // id of the temporary customer
		printf("customer%d come in\n" , tempCustomerID);              
		
		if( waiting >= chair) {
			printf("not enough chair for customer to wait , customer%d leave\n" , tempCustomerID);
			tem_v(0, &mutex , &proc_table[i + 1]);                     // leave the mutex                       
			continue;
		}else{
			waiting++;
			printf("now waiting people is %d\n" , waiting);
			tem_v(0 , &customer , 0);                                //wake up the barber
			tem_v(0 , &mutex    , 0);
			
			tem_p(tempCustomerID , &barber   , &proc_table[i + 1]);                  //if the barber is busy , sleep
			printf("customer%d get service\n" , tempCustomerID);
			
		}
		



	}
}
/*======================================================================*
                               Customer2
 *======================================================================*/
void Customer2()
{	
	int i = 3;
	int tempCustomerID;
	while(1){
		milli_delay(customer2ComeTime);                                // the next time before the customer come


		tem_p(0 , &mutex , &proc_table[i+1]);                         //go in the critical section
		tempCustomerID = ++customerID;                                // id of the temporary customer
		printf("customer%d come in\n" , tempCustomerID);              
		
		if( waiting >= chair) {
			printf("not enough chair for customer to wait , customer%d leave\n" , tempCustomerID);
			tem_v(0, &mutex , &proc_table[i + 1]);                     // leave the mutex                       
			continue;
		}else{
			waiting++;
			printf("now waiting people is %d\n" , waiting);
			tem_v(0 , &customer , 0);                                //wake up the barber
			tem_v(0 , &mutex    , 0);
			
			tem_p(tempCustomerID , &barber   , &proc_table[i + 1]);                  //if the barber is busy , sleep
			printf("customer%d get service\n" , tempCustomerID);
			
		}
		



	}
}
/*======================================================================*
                               Customer3
 *======================================================================*/
void Customer3()
{
	int i = 4;
	int tempCustomerID;
	while(1){
		milli_delay(customer3ComeTime);                                // the next time before the customer come


		tem_p(0 , &mutex , &proc_table[i+1]);                         //go in the critical section
		tempCustomerID = ++customerID;                                // id of the temporary customer
		printf("customer%d come in\n" , tempCustomerID);              
		
		if( waiting >= chair) {
			printf("not enough chair for customer to wait , customer%d leave\n" , tempCustomerID);
			tem_v(0, &mutex , &proc_table[i + 1]);                     // leave the mutex                       
			continue;
		}else{
			waiting++;
			printf("now waiting people is %d\n" , waiting);
			tem_v(0 , &customer , 0);                                //wake up the barber
			tem_v(0 , &mutex    , 0);
			
			tem_p(tempCustomerID , &barber   , &proc_table[i + 1]);                  //if the barber is busy , sleep
			printf("customer%d get service\n" , tempCustomerID);
			
		}
		



	}
}
