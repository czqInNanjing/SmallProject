//
// Created by Qiang Chen on 20/03/2017.
// This file emulate the implementation of 'wc'
//
#include <iostream>
#include <sys/stat.h>


using namespace std;

int wc(const char *path);

int countWord(FILE *file);

int countLine(FILE *file);

int main(int argc, char **argv) {


    if (strcmp(argv[0], "./wc") == 0) {

        if(argc == 1) {
            cerr << "Please specific the file" << endl;
        } else {
            return wc(argv[1]);
        }


    } else {
        cout << "You have imput the wrong command, please input again" << endl;
    }

    //    wc("/Users/czq/Documents/OneDrive/Junior Second/Linux Program Design/test");
    return 0;
}


int wc(const char *path) {

    struct stat st;

    if (stat(path, &st) < 0) {
        cerr << "wc:" << path << ":No such file or directory" << endl;
        return -1;
    }
    else if (S_ISDIR(st.st_mode & S_IFMT)) {

        printf("wc: %s is a directory\n", path);

    } else {
        FILE *file = fopen(path, "r");
        if (file != NULL) {
            int wordNum = countWord(file);
            rewind(file); // set back the pointer of the file stream to the original position
            int lineNum = countLine(file);
            fclose(file);
            cout << lineNum << " " << wordNum << " " << st.st_size << " " << path << endl;
        } else {
            cerr << "Fail to open the file" << endl;
            return -1;
        }


    }


    return 0;
}

int countWord(FILE *file) {
    int wordCount = 0;
    int c;
    bool isInWord = false;
    while ((c = fgetc(file)) != EOF) {
        if (isalpha(c) && !isInWord) {
            isInWord = true;
        } else if (!isalpha(c) && isInWord) {
            wordCount++;
            isInWord = false;
        }
    }


    return wordCount;
}

int countLine(FILE *file) {
    int lineNum = 0;
    int c;
    while ((c = fgetc(file)) != EOF) {
        if (c == '\n') {
            lineNum++;
        }
    }


    return lineNum;
}