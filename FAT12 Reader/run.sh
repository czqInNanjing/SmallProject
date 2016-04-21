#!/bin/bash
nasm -f elf32 -o print.o my_print.asm
gcc -m32 main.c print.o 
./a.out