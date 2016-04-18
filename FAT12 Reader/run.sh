#!/bin/bash
nasm -f elf32 -o print.o nasm_print.asm
gcc -m32 main.c print.o 
./a.out