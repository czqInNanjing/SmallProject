cmake_minimum_required(VERSION 3.5)
project(qiangLinuxDesignHome)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES ls.cpp wc.cpp )
add_executable(qiangLinuxDesignHome ${SOURCE_FILES} wc.cpp)