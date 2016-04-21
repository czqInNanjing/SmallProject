global n_print

	section .text
n_print:
	mov ecx , [esp]
	mov edx , [esp + 4]

print:
	mov eax , 4
	mov ebx , 1
	int 80h
