/*
*********************************************************************************************************
*                                            EXAMPLE CODE
*
*                          (c) Copyright 2009-2011; Micrium, Inc.; Weston, FL
*
*               All rights reserved.  Protected by international copyright laws.
*
*               Please feel free to use any application code labeled as 'EXAMPLE CODE' in
*               your application products.  Example code may be used as is, in whole or in
*               part, or may be used as a reference only.
*
*               Please help us continue to provide the Embedded community with the finest
*               software available.  Your honesty is greatly appreciated.
*
*               You can contact us at www.micrium.com.
*********************************************************************************************************
*/




/*
*********************************************************************************************************
*
*                                          APPLICATION CODE
*
*                                          Microsoft Windows
*
* Filename      : app.c
* Version       : V1.00
* Programmer(s) : JJL
*                 FGK
*********************************************************************************************************
*/

/*
*********************************************************************************************************
*                                             INCLUDE FILES
*********************************************************************************************************
*/

#include <includes.h>


/*
*********************************************************************************************************
*                                             LOCAL DEFINES
*********************************************************************************************************
*/

/*
*********************************************************************************************************
*                                            LOCAL VARIABLES
*********************************************************************************************************
*/

static  CPU_STK  AppTaskStartStk[APP_TASK_START_STK_SIZE];


/*
*********************************************************************************************************
*                                         FUNCTION PROTOTYPES
*********************************************************************************************************
*/

static  void  AppTaskStart(void  *p_arg);

/*
*********************************************************************************************************
*                                         DEFINED FOR EDF ALGORITHM BY QIANG
*********************************************************************************************************
*/
#define TASK_STACK_SIZE 1024  

/*---   stacks ---*/
OS_STK TASK1STK[TASK_STK_SIZE];
OS_STK TASK2STK[TASK_STK_SIZE];

//TASK ID and Priority 
#define TASK1_ID 1
#define TASK2_ID 2
#define TASK3_ID 3
#define TASK4_ID 4
#define TASK5_ID 5

// c, p, compTime, ddl, start, end
EDF_TASK_DATA taskData[] {
    {1,3,1,4,0,0},          // task1
    {3,5,3,6,0,0}           // task2
}


/*
*********************************************************************************************************
*                                                main()
*
* Description : This is the standard entry point for C code.  It is assumed that your code will call
*               main() once you have performed all necessary initialization.
*
* Argument(s) : none.
*
* Return(s)   : none.
*********************************************************************************************************
*/

int  main (void)
{
    OSInit();                                                   /* Init uC/OS-II.                                       */

    // OSTaskCreateExt((void(*)(void *))AppTaskStart,              /* Create the start task                                */
    //     (void          *) 0,
    //     (OS_STK        *)&AppTaskStartStk[APP_TASK_START_STK_SIZE - 1],
    //     (INT8U          ) APP_TASK_START_PRIO,
    //     (INT16U         ) APP_TASK_START_PRIO,
    //     (OS_STK        *)&AppTaskStartStk[0],
    //     (INT32U         ) APP_TASK_START_STK_SIZE,
    //     (void          *) 0,
    //     (INT16U         )(OS_TASK_OPT_STK_CHK | OS_TASK_OPT_STK_CLR));

    OSTaskCreateExt((void(*)(void *))task1,
        (void          *) 0,
        (OS_STK        *)&TASK1STK[TASK_STACK_SIZE-1],
        (INT8U          ) TASK_1_ID,
        (INT16U         ) TASK_1_ID,
        (OS_STK        *)&TASK1STK[0],
        (INT32U         ) TASK_STACK_SIZE,
        (void          *)&taskData[0],                 // TCBext
        (INT16U         )0);

    OSTaskCreateExt((void(*)(void *))task2,
        (void          *) 0,
        (OS_STK        *)&TASK1STK[TASK_STACK_SIZE-1],
        (INT8U          ) TASK_2_ID,
        (INT16U         ) TASK_2_ID,
        (OS_STK        *)&TASK2STK[0],
        (INT32U         ) TASK_STACK_SIZE,
        (void          *)&taskData[1],
        (INT16U         )0);


    OSStart();                                                  /* Start multitasking (i.e. give control to uC/OS-II).  */
}


/*
*********************************************************************************************************
*                                          STARTUP TASK
*
* Description : This is an example of a startup task.  As mentioned in the book's text, you MUST
*               initialize the ticker only once multitasking has started.
*
* Argument(s) : p_arg       is the argument passed to 'AppTaskStart()' by 'OSTaskCreate()'.
*
* Return(s)   : none.
*
* Note(s)     : (1) The first line of code is used to prevent a compiler warning because 'p_arg' is not
*                   used.  The compiler should not generate any code for this statement.
*********************************************************************************************************
*/

static  void  AppTaskStart (void *p_arg)
{
    OS_ERR  err;


   (void)p_arg;

    BSP_Init();                                                 /* Initialize BSP functions                             */
    CPU_Init();                                                 /* Initialize uC/CPU services                           */

#if OS_CFG_STAT_TASK_EN > 0u
    OSStatTaskCPUUsageInit(&err);                               /* Compute CPU capacity with no task running            */
#endif

    APP_TRACE_DBG(("uCOS-II is Running...\n\r"));

    while (DEF_ON) {                                            /* Task body, always written as an infinite loop.       */
        OSTimeDlyHMSM(0, 0, 1, 0);

        APP_TRACE_DBG(("Time: %d\n\r", OSTimeGet(&err)));
    }
}
