//
// Created by Qiang Chen on 20/03/2017.
// This file emulate the implementation of 'ls'
//
#include <iostream>
#include <sys/stat.h>
#include <grp.h>
#include <pwd.h>
#include <dirent.h>
#include <vector>
#include <unistd.h>

using namespace std;

// to show month
const char *months[] = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

class FileOrDir {
public:
    string name;
    string path;
    bool isDir;

    FileOrDir(string _name, string _path, bool _isDir) {
        this->name = _name;
        this->path = _path;
        this->isDir = _isDir;
    }


};


//use current path if path is not specify
char *getcwd(char *buf, size_t size);
// main function
int ls_default(const char *path);

int ls_l(const char *path, int mode, int omitSecretFile, bool showSerialNum);

int ls_d(const char *path);

int ls_R(const char *path);

int ls_a(const char *path);

int ls_i(const char *path);


const int SIMPLE_MODE = 0;
const int DETAIL_MODE = 1;
const int OMIT_SECRET_FILE = 0;
const int NOT_OMIT_SECRET_FILE = 1;
const int SHOW_SERIAL_NUM = 0;
const int NOT_SHOW_SERIAL_NUM = 1;

//help function

/**Display a file in detail*/
int display_FILE(const char *path, const char *filename, int mode, bool showSerialNum);

int display_DIR(const char *dirPath, int mode, int omitSecretFile, bool showSerialNum);
vector<FileOrDir>* getAllSubFilesOrDir(const char* path);



int main(int argc, char **argv) {





    if(strcmp(argv[0], "./ls") == 0){
        char filePath[1024];
        getcwd(filePath, sizeof(filePath));  // get current path

        // cout << argc << endl;
        if (argc == 1) {
            // cout << "No path specify, use current path instead" << endl; 
            // use current path           
            ls_default(filePath);
        }  else if (argc == 2) {
            if(strcmp(argv[1], "-l") == 0)
                ls_l(filePath, DETAIL_MODE, OMIT_SECRET_FILE, NOT_SHOW_SERIAL_NUM);
            else if(strcmp(argv[1], "-d") == 0)
                ls_d(filePath);
            else if(strcmp(argv[1], "-R") == 0)
                ls_R(filePath);
            else if(strcmp(argv[1], "-a") == 0)
                ls_a(filePath);
            else if(strcmp(argv[1], "-i") == 0)
                ls_i(filePath);
            else {
                ls_default(argv[1]);
            }
        }  else {
            if(strcmp(argv[1], "-l") == 0)
                ls_l(argv[2], DETAIL_MODE, OMIT_SECRET_FILE, NOT_SHOW_SERIAL_NUM);
            else if(strcmp(argv[1], "-d") == 0)
                ls_d(argv[2]);
            else if(strcmp(argv[1], "-R") == 0)
                ls_R(argv[2]);
            else if(strcmp(argv[1], "-a") == 0)
                ls_a(argv[2]);
            else if(strcmp(argv[1], "-i") == 0)
                ls_i(argv[2]);
            else
                cout << "Command Not support" << endl;
        }


        


    } else {
        cout << "You have imput the wrong command, please input again" << endl;
    }






//    cout << display_FILE("/Users/czq/Documents/books/Mastering_Regular_Expressions.pdf",
//                         "/Users/czq/Documents/books/Mastering_Regular_Expressions.pdf", 0) << endl;
//
//    ls_l("/Users/czq/Documents", SIMPLE_MODE);
//    ls_R("/Users/czq/Documents/OneDrive/Junior Second/Linux Program Design");
//    ls_i("/Users/czq/Documents/OneDrive/Junior Second/Linux Program Design");
    return 0;
}

/* **********************************************************************
 *                     ls: default implementation of ls
 * **********************************************************************
 */
int ls_default(const char *path) {
    return ls_l(path, SIMPLE_MODE, OMIT_SECRET_FILE, NOT_SHOW_SERIAL_NUM);
}

/* **********************************************************************
 *                             ls -l
 * **********************************************************************
 */
int ls_l(const char *path, int mode, int omitSecretFile, bool showSerialNum) {
    struct stat stt;
    if (stat(path, &stt) < 0) {
        printf("ls: %s:No such file or directory\n", path);
        return -1;
    }

    if (S_ISDIR(stt.st_mode & S_IFMT)) {
        display_DIR(path, mode, omitSecretFile, showSerialNum);
    } else {
        display_FILE(path, path, mode,showSerialNum);
    }
    return 0;
}

/* **********************************************************************
 *  display_DIR
 *  @param mode: 0 means simple mode(only name) while 1 is detail mode
 *
 * **********************************************************************
 */
int display_DIR(const char *dirPath, int mode, int omitSecretFile, bool showSerialNum) {
    DIR *dir;
    dir = opendir(dirPath);
    struct dirent *dirent;

    string *oneFilePath;
    struct stat st;
    while ((dirent = readdir(dir)) != NULL) {
        oneFilePath = new string(dirPath);
        *oneFilePath += "/";
        *oneFilePath += dirent->d_name;

        if (stat(oneFilePath->c_str(), &st) < 0) {
            printf("ls: %s:No such file or directory\n", dirPath);
            return -1;
        }
        if (omitSecretFile == OMIT_SECRET_FILE) {
            if (dirent->d_name[0] != '.') {  // not display any secret file
                display_FILE(oneFilePath->c_str(), dirent->d_name, mode, showSerialNum);
            }
        } else {
            display_FILE(oneFilePath->c_str(), dirent->d_name, mode, showSerialNum);
        }


    }
    cout << endl;
    return 0;
}

/* **********************************************************************
 *                             getAllSubFilesOrDir
 *              return all sub files or dirs of the current path
 * **********************************************************************
 */
vector<FileOrDir>* getAllSubFilesOrDir(const char* path) {

    vector<FileOrDir>* result = new vector<FileOrDir>();

    DIR *dir = opendir(path);
    struct dirent *dirent;
    string *oneFilePath;
    while ((dirent = readdir(dir)) != NULL ) {
        oneFilePath = new string(path);
        *oneFilePath += "/";
        *oneFilePath += dirent->d_name;

        bool isDir = false;
        struct stat st;
        if (stat(oneFilePath->c_str(), &st) >= 0) {
            isDir = S_ISDIR(st.st_mode & S_IFMT);
            FileOrDir* fileOrDir = new FileOrDir(dirent->d_name , *oneFilePath, isDir);
            result->push_back(*fileOrDir);
        }

    }

    return result;

}


/* **********************************************************************
 *  display_FILE
 *  @param mode: 0 means simple mode(only name) while 1 is detail mode
 *
 * **********************************************************************
 */
int display_FILE(const char *path, const char *filename, int mode, bool showSerialNum) {
    struct stat st;
    if (stat(path, &st) >= 0) {
        struct passwd *pw;
        struct group *gr;
        struct tm *tm;

        if (mode) {
            // show file type
            switch (st.st_mode & S_IFMT) {
                case S_IFREG:
                    printf("-");
                    break;
                case S_IFDIR:
                    printf("d");
                    break;
                case S_IFLNK:
                    printf("l");
                    break;
                case S_IFBLK:
                    printf("b");
                    break;
                case S_IFCHR:
                    printf("c");
                    break;
                case S_IFIFO:
                    printf("p");
                    break;
                case S_IFSOCK:
                    printf("s");
                    break;
                default:
                    break;
            }


            // show privileges
            for (int i = 8; i >= 0; i--) {
                if (st.st_mode & (1 << i)) {
                    switch (i % 3) {
                        case 2:
                            printf("r");
                            break;
                        case 1:
                            printf("w");
                            break;
                        case 0:
                            printf("x");
                            break;
                        default:
                            break;
                    }
                }
                else
                    printf("-");
            }


            pw = getpwuid(st.st_uid);
            gr = getgrgid(st.st_gid);


            printf("%2d %s %s %4ld", st.st_nlink, pw->pw_name, gr->gr_name, st.st_size);

            tm = localtime(&st.st_ctime);
            printf(" %s %2d %2d:%2d", months[tm->tm_mon], tm->tm_mday, tm->tm_hour, tm->tm_min);
            printf(" %s\n", filename);

        } else {
            if (showSerialNum == SHOW_SERIAL_NUM) {
                printf("%9ld " ,  st.st_ino);
            }


            printf("%s ", filename);
        }


        return 0;
    } else {
        return -1;
    }

}


/* **********************************************************************
 *                             ls -d
 * **********************************************************************
 */
int ls_d(const char *path) {
    struct stat st;

    if (stat(path, &st) < 0) {
        printf("ls: %s:No such file or directory\n", path);
        return -1;
    }
    else {
        printf("%s\n", path);
    }
    return 0;
}
/* **********************************************************************
 *                             ls -R
 * **********************************************************************
 */
int ls_R(const char *path) {
    struct stat stt;
    if (stat(path, &stt) < 0) {
        printf("ls: %s:No such file or directory\n", path);
        return -1;
    }

    if (S_ISDIR(stt.st_mode & S_IFMT)) {
        // if a directory, first print all the content in this directory
        // TODO need to deal with the unlimited recursive call on '.'(itself) when not omit the secret file, temporarily we only support showing not secret file while recursive call
        display_DIR(path, SIMPLE_MODE, OMIT_SECRET_FILE, NOT_SHOW_SERIAL_NUM);

        cout << endl;
//        cout << path << endl;
        vector<FileOrDir>* subList = getAllSubFilesOrDir(path);

        for(vector<FileOrDir>::const_iterator fileIter= subList->begin(); fileIter!=subList->end(); fileIter++) {
            if (fileIter->isDir && fileIter->name[0] != '.') { // omit all secret files
                cout << "./" << fileIter->name << endl;
                ls_R(fileIter->path.c_str());
            }

        }



    } else {
        display_FILE(path, path, SIMPLE_MODE, false);
    }
    return 0;
}

/* **********************************************************************
 *                             ls -a
 * **********************************************************************
 */
int ls_a(const char *path) {
    return ls_l(path, SIMPLE_MODE, NOT_OMIT_SECRET_FILE, NOT_SHOW_SERIAL_NUM);
}

/* **********************************************************************
 *                             ls -i
 * **********************************************************************
 */
int ls_i(const char *path) {
    return ls_l(path, SIMPLE_MODE, OMIT_SECRET_FILE, SHOW_SERIAL_NUM);
}

