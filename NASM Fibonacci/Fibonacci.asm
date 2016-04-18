;斐波那契数列
;思路：1、逐个读取字符
;     2、
;
;
;
global main
section .data
	str: db "Please input the number",0ah
	strlen: equ $-str
	outstr: db "The result is :",0ah
	outstrlen: equ $-outstr
	len: dw 30
	numlen : db 0
	nums : db 0
	divider : dq 10
	bigdivider : dq 10
	innum  : dq 0 ; 保存当前输入的是第几个数字
	outnum : dq 0 ; 保存当前输出的是第几个数字
	color : db `\033[1;31m` , 0

section .bss
	readin: resb 20
	digit1:resb 1 
	digit2:resb 1 
	digit3:resb 1 
	digit4:resb 1 
	array:  resw 10 ; 保存读入的数字
	temp : resb 1
	outarray : resq 20 ; 保存计算出来的数字
	op1 : resb 16	   ; 斐波那契数列计算操作符1
	op2 : resb 16 	   ; 斐波那契数列计算操作符2
	quotient : resq 1  ; 大数除法时将商暂时存储起来
	
section .text



main:
	;prompt message
	call   getBigDivider
	mov eax, 4
	mov ebx, 1
	mov ecx, str
	mov edx, strlen
	int 80h

	mov rbx, array
	
	;read data from console
Reading:
	push rbx

    mov byte[numlen] , 1

	mov eax, 3
	mov ebx, 0
	mov ecx, digit1
	mov edx, 1
	int 80h
	
	mov eax, 3
	mov ebx, 0
	mov ecx, digit2
	mov edx, 1
	int 80h

	cmp byte[digit2] , 32
	je  endofonedigit
	cmp byte[digit2] , 10
	je  endofread
	inc byte[numlen]

	mov eax, 3
	mov ebx, 0
	mov ecx, digit3
	mov edx, 1
	int 80h

	cmp byte[digit3] , 32
	je  endofonedigit
	cmp byte[digit3] , 10
	je  endofread
	inc byte[numlen]

	mov eax, 3
	mov ebx, 0
	mov ecx, digit4
	mov edx, 1
	int 80h

	cmp byte[digit4] , 32
	je endofonedigit
	cmp byte[digit4] , 10
	je  endofread
	inc byte[numlen]

endofonedigit:
	pop rbx ; rbx用来存储当前的数

	cmp byte[numlen] , 1
	jne l2
	sub byte[digit1] , 30h
    movzx dx , byte[digit1]
    mov   word[rbx] , dx
    add  rbx , 2
    inc byte[nums] 
    jmp Reading
l2:
	cmp byte[numlen] , 2
	jne l3
	sub byte[digit1] , 30h
	movzx dx , byte[digit1]
	imul dx ,10
	sub byte[digit2] , 30h
	movzx cx , byte[digit2]
	add dx , cx
	mov   word[rbx] , dx
    add  rbx , 2
    inc byte[nums]
    jmp Reading
l3:
	sub   byte[digit1] , 30h
	movzx dx , byte[digit1]
	imul  dx,100
	sub   byte[digit2] , 30h
	movzx cx ,byte[digit2]
	imul  cx,10
	add   dx,cx
	sub   byte[digit3] , 30h
	movzx cx , byte[digit3]
	add   dx , cx
	mov   word[ebx] , dx
    add   rbx , 2
    inc   byte[nums]
    jmp   Reading

	

	
endofread:	
	pop   rbx ; ebx用来存储当前的数
	cmp   byte[numlen] , 1
	jne   ll2
	sub   byte[digit1] , 30h
    movzx dx , byte[digit1]
    mov   word[ebx] , dx
    inc   byte[nums] 
    jmp   end
ll2:
	cmp   byte[numlen] , 2
	jne   ll3
	sub   byte[digit1] , 30h
	movzx dx , byte[digit1]
	imul  dx ,10
	sub   byte[digit2] , 30h
	movzx cx , byte[digit2]
	add   dx , cx
	mov   word[ebx] , dx
    inc   byte[nums]
    jmp   end
ll3:
	sub   byte[digit1] , 30h
	movzx dx , byte[digit1]
	imul  dx,100
	sub   byte[digit2] , 30h
	movzx cx ,byte[digit2]
	imul  cx,10
	add   dx,cx
	sub   byte[digit3] , 30h
	movzx cx , byte[digit3]
	add   dx , cx
	mov   word[ebx] , dx
    inc   byte[nums]

end:
	
	xor   rcx ,rcx
    movzx ecx, byte[nums]
    

compute:
	push   rcx
	mov    rbx , array
	add    rbx , qword[innum]
	movzx  rax , word[ebx]
	push   rax
	add    qword[innum] ,2
	jmp    fi
fiFinish:
	mov    rbx , outarray
	add    rbx , qword[outnum]
	mov    qword[rbx] , rdx             ;  高8位
	mov    qword[rbx + 8] , rax		  ;  低八位
	add    qword[outnum] ,16
	pop    rcx
	loop   compute


	;prompt message
	mov eax, 4
	mov ebx, 1
	mov ecx, outstr
	mov edx, outstrlen
	int 80h


	; jmp    exit
	movzx  rcx , byte[nums]
	mov    qword[outnum] , 0
showAllOutputNum:

	push   rcx
 	mov    rbx , outarray
 	add    rbx , qword[outnum]
 	mov    rdx , qword[rbx]
 	mov    rax , qword[rbx + 8]
 	add    qword[outnum] , 16
 	inc    byte[color + 5]
 	call   showOneNum


 	mov byte[temp] , 10
 	mov eax , 4
	mov ebx , 1
	mov ecx , temp
	mov edx , 1
	int 80h



 	pop  rcx
 	loop showAllOutputNum
 	jmp exit






showOneNum:
	
	

	xor rcx ,rcx ; 用来计算数的位数
	xor rbx ,rbx
	div qword[bigdivider]
	cmp rax , 0
	jne  bigdiv     ; >= 92

	mov rax , rdx     ;   
	xor rdx , rdx
	
next:
	div qword[divider]
	inc rcx
	add rdx , 30h
	push rdx
	xor rdx ,rdx
	cmp rax , 0
	jne next

output:
	pop rdx
	mov byte[color + 7] , dl
	push rcx

	mov eax , 4
	mov ebx , 1
	mov ecx , color
	mov edx , 8
	int 80h

	pop rcx
	loop output
	ret

bigdiv:
	mov qword[quotient] , rax
	mov rax , rdx
	xor rdx , rdx
	xor rbx , rbx

bnext:
	div qword[divider]
	inc rcx
	add rdx , 30h
	push rdx
	xor rdx ,rdx
	cmp rax , 0
	jne bnext

addTO18: 
	cmp rcx , 19
	jnl continue

	push 30h
	inc rcx
	jmp addTO18



continue:	
	mov rax , qword[quotient]
	xor rdx , rdx
         ;使后面小的数达到18位，因为第一位可能为0 ， 例如f(102) = 150||0520536206896083277，此时第一个0不输出
	



bnext2:
	div qword[divider]
	inc rcx
	add rdx , 30h
	push rdx
	xor rdx ,rdx
	cmp rax , 0
	jne bnext2


boutput:
	pop rdx
	mov byte[color + 7] , dl
	push rcx

	mov eax , 4
	mov ebx , 1
	mov ecx , color
	mov edx , 8
	int 80h

	pop rcx
	loop boutput
	ret


exit:	 
	mov eax , 1
	mov ebx , 0
	int 80h
	
fi:	
	xor    rdx ,rdx
    pop    rcx

    cmp    rcx , 1
    jng    ret1

    dec    rcx   

    mov    r9 , 1
    mov    r10 , 0
    mov    r11 , 1
    mov    r12 , 0  ;    op1 = r10:r9   op2 = r12:r11




fiin:
	
	clc
	add    r9 , r11
	adc    r10 ,r12

	mov    r13 ,r9
	mov    r14 , r10
	mov    r9 , r11
	mov    r10 , r12
	mov    r11 , r13
	mov    r12  , r14


	loop  fiin
	mov   rax , r11
	mov   rdx , r12
	jmp   fiFinish

ret1:
	mov rax  , 1
	mov rdx  , 0
	jmp   fiFinish


getBigDivider:   ;获得10的19次方
	push rax
	push rcx
	push rdx
	mov  rax , 10
	mov  rcx , 18
	mov  rdx , 0
multi:
	mul qword[bigdivider]
	loop multi

	mov  qword[bigdivider] , rax
	pop  rdx
	pop  rcx
	pop  rax

	ret


	 