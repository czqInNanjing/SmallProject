//
// Created by Qiang Chen on 4/16/16.
//
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <stdarg.h>


typedef unsigned char u8;   //1字节
typedef unsigned short u16; //2字节
typedef unsigned int u32;   //4字节
/************************常量及全局变量*************************/
//const char* filePath = "/home/qiang/OS2/abc.img";
const char* filePath = "/Users/czq/Development/OS/123/abc.img";

// 是否为纯C语言,此开关打开时将使用C类库代替nasm汇编语言,还需要注释掉661,662行
int pureC = 1;
/***颜色控制***/
char* white = "\e[0m";
char* red = "\e[31m";
char* green = "\e[32m";
//字符串结束标志,用于自己的print程序
char* overStr = "/0";
char* split_Char = "/";
//镜像文件
FILE* file;
//数据区偏移开始
int DATA_BASE;
/*****BPB****/
int  BytsPerSec;    //每扇区字节数
int  SecPerClus;    //每簇扇区数
int  RsvdSecCnt;    //Boot记录占用的扇区数
int  NumFATs;   //FAT表个数
int  RootEntCnt;    //根目录最大文件数
int  FATSz; //FAT扇区数
/*****BPB****/


#pragma pack (1) /*指定按1字节对齐*/
struct BPB    //  引导扇区
{
    u16  BPB_BytsPerSec;    //每扇区字节数
    u8   BPB_SecPerClus;    //每簇扇区数
    u16  BPB_RsvdSecCnt;    //Boot记录占用的扇区数
    u8   BPB_NumFATs;   //FAT表个数
    u16  BPB_RootEntCnt;    //根目录最大文件数
    u16  BPB_TotSec16;
    u8   BPB_Media;
    u16  BPB_FATSz16;   //FAT扇区数
    u16  BPB_SecPerTrk;
    u16  BPB_NumHeads;
    u32  BPB_HiddSec;
    u32  BPB_TotSec32;  //如果BPB_FATSz16为0，该值为FAT扇区数
}bpb;
//BPB至此结束，长度25字节

//根目录条目
struct RootEntry {
    char DIR_Name[11];
    u8   DIR_Attr;      //文件属性  0x20 = file 0x10 =dir
    char reserved[10];
    u16  DIR_WrtTime;
    u16  DIR_WrtDate;
    u16  DIR_FstClus;   //开始簇号
    u32  DIR_FileSize;  //file size
}rootEntry;
//根目录条目结束，32字节

#pragma pack () /*取消指定对齐，恢复缺省对齐*/
//精简版文件信息  文件具体内容需要从数据区读
struct FILE_INFO{
    char name[12];
    int isFolder;
    int start;
    int fileSize;
};


struct TreeNode{
    struct FILE_INFO file_info;
    struct TreeNode* parent;
    struct TreeNode* firstChild;
    struct TreeNode* next;
};
struct TreeNode* root;//根目录节点
struct BPB* bpb_ptr;



/************************方法声明*************************/
void fill_BPB();//读取BPB内容
void getRootEntry(struct RootEntry*[] , int len,int); //读取所有根目录条目
void printAllRootEntrys(struct RootEntry*[] , int len);//打印所有根目录输出(测试使用)
void initTree(struct RootEntry*[] , int len); //初始化目录结构
void initATree(struct RootEntry*[] , int len , struct TreeNode*); //对每个目录,递归初始化其目录
void printFileDirecs() ;//打印所有文件和目录
void printDir(char* path , struct TreeNode* root, int);
void printAllChildren(char * path , struct TreeNode* father);
int isUsefulFile(char* fileName);//该文件是否有效,过滤一些非普通文件

void getValidName(char* des , char* src);//获得有效的名字,去除逗号等等
void countTheDirec(char* userInput);//计数目录数量
void printFileOrDire(char* userInput);//根据用户输入
struct TreeNode* getNode(struct TreeNode* root ,char * );//根据根节点获得其某个子节点,Null则表示没有匹配的

void printFile(int start , int size) ;//打印文件内容

/*根据扇区获得其基址*/
int getBase(int start) ;
void printFilesAndDirects(char *name, int files, int directories, int space);
/****计算目录及其子目录的文件数量****/
void countFilesAndDire(struct TreeNode* myRoot , int level);
/***手敲的输出***/
void my_print(char* msg ,...);
extern void n_print(char* str , int len);//链接nasm汇编语言
/***Other***/
char* itoa(int num,char*str,int radix);//整数转化为字符串
int getNextFatValue(int now);//获得下一个簇位置



/************************代码区*************************/

int main(){
    file = fopen(filePath,"rb");
    if(file==NULL)
    {
        my_print("open file test.txt failed!\n" , overStr);
        exit(1);
    }
    bpb_ptr = &bpb;
    fill_BPB();
    DATA_BASE = BytsPerSec * ( RsvdSecCnt + FATSz*NumFATs + (RootEntCnt*32 + BytsPerSec - 1)/BytsPerSec );

    char  userInput[100];
    //printf("%d" ,DATA_BASE);//数据层开始于33扇区
    struct RootEntry* roots[RootEntCnt];
    int base = (RsvdSecCnt + NumFATs * FATSz) * BytsPerSec;
    getRootEntry(roots , RootEntCnt ,base);
//    printAllRootEntrys(roots, RootEntCnt);

    root = malloc(sizeof(struct TreeNode));

    initTree(roots , RootEntCnt );

    printFileDirecs();

    char count[] = "count";
    while (1){
        my_print("Please input the order: \n" ,overStr);
        scanf("%s",userInput);

        if(!strcmp(userInput , "q")){
            my_print("Bye\n" , overStr);
            exit(0);
        }

        if(!strncmp(userInput,count , 5 )){
            scanf("%s" , userInput);
            countTheDirec(userInput);
        } else{
            printFileOrDire(userInput);
        }




    }

}



void printFileOrDire(char* userInput) {
    char * copy = malloc(strlen(userInput)+1);
    strcpy(copy , userInput);
    char* temp = strtok(userInput , split_Char) ;
    if(temp == NULL){
        my_print("Errot" , overStr);
    }



    struct TreeNode* myRoot = root;
    while (temp != NULL){
        myRoot = getNode(myRoot , temp);
        if(myRoot == NULL){
            my_print("ERROR! Not Any File or Directory match this name.\n" , overStr);
            return;
        }
        temp = strtok(NULL , split_Char);
    }
//    printf("It's a file %s" , myRoot->file_info.name);
    if(myRoot->file_info.isFolder){
        if(myRoot->file_info.fileSize == 0){
            printDir(copy , myRoot , 1);
        } else{
            printAllChildren(copy , myRoot);
        }


    } else{
        printFile(myRoot->file_info.start , myRoot->file_info.fileSize) ;
    }



}

void printFile(int start , int size) {
    char fileContenet[size + 1];
    for (int j = 0; j < size + 1; ++j) {
        fileContenet[j] = '\0';
    }
    char oneSec[BytsPerSec + 1];
    oneSec[BytsPerSec] = '\0';
    int check;
    check = fseek(file , getBase(start) , SEEK_SET);
    int num = size/BytsPerSec + 1;
    if(check == - 1){
        my_print("Read File Fail\n", overStr);
    }
    if(size <= 512){
        check = fread(fileContenet , 1 , size , file);
        if(check != size){
            my_print("Fail to read", overStr);
        }
        fileContenet[size] = '\0';
        printf(fileContenet);
        return;
    }

    for (int i = 0; i < num; ++i) {

        if(start == 0xFF7){
            my_print("文件损坏\n", overStr);
            return;
        }else if(start == 0xff8){
            my_print("文件意外终止", overStr);
            return;
        }

        check = fseek(file , getBase(start) ,SEEK_SET);
        if(i == num -1){
            fread(oneSec , 1 , size%BytsPerSec , file);
            strncat(fileContenet , oneSec , size%BytsPerSec);
        } else{
            fread(oneSec , 1 , BytsPerSec , file);
            strncat(fileContenet , oneSec , BytsPerSec);
        }

        fileContenet[size] = '\0';
//        fsileContenet[BytsPerSec*(num + 1) + 1] ='\0';
        start = getNextFatValue(start);

    }
    my_print(fileContenet, overStr);
    my_print("\n", overStr);

}

void countTheDirec(char* userInput) {
    char* path = malloc(strlen(userInput));
    strcpy(path , userInput);
    char* temp = strtok(path , split_Char) ;
    if(temp == NULL){
        my_print("Errot", overStr);
    }



    struct TreeNode* myRoot = root;
    while (temp != NULL){
        myRoot = getNode(myRoot , temp);
        if(myRoot == NULL){
            my_print("ERROR!Counting File Not Any File or Directory match this name.\n", overStr);
            return;
        }
        temp = strtok(NULL , split_Char);
    }

    if(myRoot->file_info.isFolder){

        countFilesAndDire(myRoot , 0);

    } else{
        my_print(userInput , overStr);
        my_print(" is not a directory.\n", overStr);
    }


}
void countFilesAndDire(struct TreeNode* myRoot , int level){
    int files = 0;
    int directories = 0;
    struct TreeNode* tempNode;
    struct TreeNode toBeShow;
    toBeShow.next = NULL;
    struct TreeNode* showNode = &toBeShow;
    if(myRoot->firstChild){
        if(myRoot->firstChild->file_info.isFolder){
            directories++;
            showNode->next = myRoot->firstChild;
            showNode = showNode->next;
        } else{
            files++;
        }
        tempNode = myRoot->firstChild;
        while (tempNode->next){
            if(tempNode->next->file_info.isFolder){
                directories++;
                showNode->next = tempNode->next;
                showNode = showNode->next;
            } else{
                files++;
            }
            tempNode = tempNode->next;
        }

//        tempNode = &toBeShow;
//        while (tempNode->next->next){
//            if(!tempNode->next->next->file_info.isFolder){
//                tempNode->next->next = NULL;   //
//                break;
//            }
//        }
    }

    printFilesAndDirects(myRoot->file_info.name , files , directories , level*4);
    level++;
    showNode = &toBeShow;
    while (showNode->next != NULL){
        if(showNode->next->file_info.isFolder){
            countFilesAndDire(showNode->next , level);
            showNode = showNode->next;
        } else{
            break;
        }

    }


}





void printFilesAndDirects(char *name, int files, int directories, int space) {
    for (int i = 0; i < space; ++i) {
        my_print(" " , overStr);
    }
    char fileNum[100];
    char directoryNum[100];
    my_print(name , " : " , itoa(files , fileNum , 10), " files , " , itoa(directories , directoryNum , 10) , " directory\n" ,overStr);
}


struct TreeNode* getNode(struct TreeNode* root , char* name){
    if(!strcasecmp(root->firstChild->file_info.name, name)){
        return root->firstChild;
    }
    struct TreeNode* temp = root->firstChild;
    while (temp->next != NULL){
        if(!strcasecmp(temp->next->file_info.name , name)){
            return temp->next;
        }
        temp = temp->next;
    }




    return NULL;
}


#pragma clang diagnostic pop

void getRootEntry(struct RootEntry* roots[], int len , int base){
//    int base = (RsvdSecCnt + NumFATs * FATSz) * BytsPerSec; //根目录首字节的偏移数



    int check;
//    char realName[12];  //暂存将空格替换成点后的文件名har realName[12];  //暂存将空格替换成点后的文件名

    for (int i = 0; i < len; ++i) {
        roots[i] = malloc(sizeof(struct RootEntry));//为所有根目录条目分配空间
    }

    for (int j = 0; j < len; ++j) {
        check = fseek(file,base,SEEK_SET);
        if (check == -1)
            my_print("fseek in printFiles failed!", overStr);

        check = (int) fread(roots[j], 1, 32, file);
        if (check != 32)
            my_print("fread in printFiles failed!", overStr);


        base += 32;
    }

}






void initTree(struct RootEntry* roots[], int len){

    initATree(roots , len , root);

    my_print("Finished initing tree\n", overStr);

}

/*根据扇区获得其基址*/
int getBase(int start) {
    return DATA_BASE + (start -2)*BytsPerSec;
}

void initATree(struct RootEntry* roots[], int len , struct TreeNode* root){
    struct TreeNode* temp;
    struct TreeNode* nodeTemp; // 存储当前树节点
    for (int i = 0; i < len; ++i) {
//
        if(roots[i]->DIR_Attr == 0x10 || roots[i]->DIR_Attr == 0x20){
            if(isUsefulFile(roots[i]->DIR_Name)){

                temp = malloc(sizeof(struct TreeNode));
                temp->parent = root;
                temp->firstChild = NULL;
                temp->next = NULL;
                temp->file_info.fileSize = roots[i]->DIR_FileSize;
                if(roots[i]->DIR_Attr == 0x10){

                    temp->file_info.isFolder = 1;
                }else{
                    temp->file_info.isFolder = 0;
                }

                temp->file_info.start = roots[i]->DIR_FstClus;
                getValidName(temp->file_info.name , roots[i]->DIR_Name);
                temp->file_info.name[11] = '\0';
                //挂在根节点上
                if(root->firstChild == NULL){
                    root->firstChild = temp;
                }else{
                    nodeTemp = root->firstChild;
                    while (nodeTemp->next != NULL){
                        nodeTemp = nodeTemp->next;
                    }
                    nodeTemp->next = temp;
                }

                if(temp->file_info.isFolder){
                    struct RootEntry* folder[16];
                    getRootEntry(folder , 16 , getBase(temp->file_info.start) );  //512/32 = 16  //不考虑子目录跨两个扇区
//                    printAllRootEntrys(folder , 16);
                    initATree(folder , 16 , temp);
                }



            }


        }



    }

}

void getValidName(char* des , char* src) {
    int addAPoint = 1;
    int j = 0;
    int splitPlace = 0;
    int isFolder = 1;
    for (int i = 0; i < 11; ++i) {

        if(isblank(src[i])){
            if(addAPoint == 1){
                addAPoint = 0;
                splitPlace = i;
            } else{
                continue;
            }

        } else if(splitPlace){
            isFolder = 0;
        }


        des[j] = src[i];
        ++j;

    }
    if(!isFolder){
        des[splitPlace] = '.';
    } else{
        des[splitPlace] = '\0';
    }
    des[++j] = '\0';
}


void printAllRootEntrys(struct RootEntry* roots[] , int len){
    for (int i = 0; i <  len; ++i) {
        if(roots[i]->DIR_Name[0] == '\0'){
            continue;
        }
        //过滤无效文件
        if(roots[i]->DIR_Attr != 0x10 && roots[i]->DIR_Attr != 0x20){
            continue;
        }
        if(isUsefulFile(roots[i]->DIR_Name)){
            printf("Dir Name %s\n" , roots[i]->DIR_Name);
            printf("Dir Attr %x\n" , roots[i]->DIR_Attr);
            printf("Start %d\n" , roots[i]->DIR_FstClus);
            printf("File Size %d\n" , roots[i]->DIR_FileSize);
        }



    }



}


void fill_BPB(){
    int check;
    check = fseek(file,11,SEEK_SET);
    if (check == -1)
        my_print("fseek in fillBPB failed!", overStr);

    //BPB长度为25字节
    check = (int) fread(bpb_ptr, 1, 25, file);
    if (check != 25)
        my_print("fread in fillBPB failed!", overStr);
    else{
        my_print("Read BPB successfully!\n", overStr);
    }

    BytsPerSec = bpb_ptr->BPB_BytsPerSec;
    SecPerClus = bpb_ptr->BPB_SecPerClus;
    RsvdSecCnt = bpb_ptr->BPB_RsvdSecCnt;
    NumFATs = bpb_ptr->BPB_NumFATs;
    RootEntCnt = bpb_ptr->BPB_RootEntCnt;
    if (bpb_ptr->BPB_FATSz16 != 0) {
        FATSz = bpb_ptr->BPB_FATSz16;
    } else {
        FATSz = bpb_ptr->BPB_TotSec32;
    }




}

void printAllChildren(char * path , struct TreeNode* father){
    printDir(path , father->firstChild , 0);
    struct TreeNode* temp = father->firstChild;
    while (temp->next != NULL){
        printDir(path , temp->next , 0);
        temp = temp->next;
    }
}


void printDir(char* path , struct TreeNode* root , int inSearch){
    if(root->file_info.isFolder){
        if(root->firstChild == NULL) { //empty folder
            my_print(red , path , overStr);
            if(!inSearch){
                my_print(red , split_Char,root->file_info.name , overStr);
            }
            my_print("\n", overStr);
        } else{
            char * temp = malloc(strlen(path) + 12);
            strncpy(temp , path , strlen(path));

            if(!inSearch){
                temp[strlen(temp)] = '/';
                strcat(temp , root->file_info.name);
            }

            printAllChildren(temp , root);
        }
    } else{
        my_print(red , path , overStr);
        my_print(white , split_Char ,root->file_info.name , "\n" , overStr);
    }
}


void printFileDirecs() {
    char temp[] = "";
    printAllChildren(temp , root);

}




int getNextFatValue(int now){
    int base = RsvdSecCnt*BytsPerSec + now*3/2;  //下一个FAT表偏移
    short next = 0;
    short * bytes = &next;
    int check = 0;
    check = fseek(file , base , SEEK_SET);
    check = fread(bytes , 1 , 2 , file);
    if(now% 2== 0){
        next = (u16) (next & 0x0FFF);

    } else{
        next =  next >> 4;
    }

    return next;


}


void my_print(char* msg ,...){
    char* temp = malloc(1000);
    for (int i = 0; i < 1000; ++i) {
        temp[i] = '\0';
    }
    strncpy(temp , msg , strlen(msg));


    va_list argp;                   /* 定义保存函数参数的结构 */
    int argno = 0;                  /* 纪录参数个数 */
    char *para;                     /* 存放取出的字符串参数 */

    /* argp指向传入的第一个可选参数，    msg是最后一个确定的参数 */
    va_start( argp, msg );

    while (1)
    {
        para = va_arg( argp, char *);                 /*    取出当前的参数，类型为char *. */
        if ( strcmp( para, overStr) == 0 )
            /* 采用空串指示参数输入结束 */
            break;
        strncat(temp , para , strlen(para));
        argno++;
    }
    va_end( argp );                                   /* 将argp置为NULL */
    if(strlen(temp) != 0){
//
        if(pureC){
            printf(temp);
        } else{
//            n_print(temp , strlen(temp));
//            n_print(white , strlen(white));
        }

    }

//    n_print(temp , strlen(temp));
//    free(temp);
}

char* itoa(int num,char*str,int radix)
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


int isUsefulFile(char* fileName){
    for (int i = 0; i < 11; ++i) {
        if(!isalnum(fileName[i]) && !isblank(fileName[i])){
            return 0;
        }
    }
    return 1;
}
