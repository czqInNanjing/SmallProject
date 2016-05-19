本作业是在Linux64位下测试通过的，故若在32位下运行，需要将makefile的编译指令更改为32位下的指令，另外挂载的位置也需要更改，请自行将61行至64行的位置更改为本机存在的位置，谢谢~

CFLAGS		= -I include/ -m32 -c -fno-builtin -fno-stack-protector
LDFLAGS		= -m elf_i386 -s -Ttext $(ENTRYPOINT)

改为Linux32位指令
CFLAGS		= -I include/  -c -fno-builtin -fno-stack-protector
LDFLAGS		= -m -s -Ttext $(ENTRYPOINT)

