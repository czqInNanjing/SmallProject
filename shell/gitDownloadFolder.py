# this is a file used for download github folder using its link
# To use it, just run it in command line with the link you copy address bar
import os
import sys

query = sys.argv[1]

# replace your specific download path here
path = '/Users/czq/Development/git/'

if 'master' in query:
    query = str.replace(query, 'tree/master', 'trunk')
else:
    query = str.replace(query, 'tree','branches')

folderName = str.split(query,'/')
folderName = folderName[len(folderName) - 1]
os.system('mkdir ' + folderName)
os.system("svn checkout "+ query + ' ' + path + '/' + folderName)

