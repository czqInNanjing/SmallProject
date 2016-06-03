
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                              vsprintf.c
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                                    Forrest Yu, 2005
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

#include "type.h"
#include "const.h"
#include "string.h"

/*
 *  为更好地理解此函数的原理，可参考 printf 的注释部分。
 */

/*======================================================================*
                                vsprintf
 *======================================================================*/
char* myitoa(int num,char*str,int radix);
int vsprintf(char *buf, const char *fmt, va_list args)
{
	char*	p;
	char	tmp[256];
	va_list	p_next_arg = args;

	for (p=buf;*fmt;fmt++) {
		if (*fmt != '%') {
			*p++ = *fmt;
			continue;
		}

		fmt++;

		switch (*fmt) {
		case 'x':
			itoa(tmp, *((int*)p_next_arg));
			strcpy(p, tmp);
			p_next_arg += 4;
			p += strlen(tmp);
			break;
		case 'd':
			myitoa(*((int*)p_next_arg) , tmp , 10);
			strcpy(p, tmp);
			p_next_arg += 4;
			p += strlen(tmp);
			break;
		case 's':
			break;
		default:
			break;
		}
	}

	return (p - buf);
}

char* myitoa(int num,char*str,int radix)
{/*索引表*/
	char index[]="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	unsigned unum;/*中间变量*/
	int i=0,j,k;
			/*确定unum的值*/
if(radix==10&&num<0)/*十进制负数*/
{
unum=(unsigned)-num;
str[i++]='-';
}
else unum=(unsigned)num;/*其他情况*/
/*转换*/
do{
str[i++]=index[unum%(unsigned)radix];
unum/=radix;
}while(unum);
str[i]='\0';
/*逆序*/
if(str[0]=='-')k=1;/*十进制负数*/
else k=0;
char temp;
for(j=k;j<=(i-1)/2;j++)
{
temp=str[j];
str[j]=str[i-1+k-j];
str[i-1+k-j]=temp;
}
return str;
}