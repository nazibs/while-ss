# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.16

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /Applications/CLion.app/Contents/bin/cmake/mac/bin/cmake

# The command to remove a file.
RM = /Applications/CLion.app/Contents/bin/cmake/mac/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = "/Users/nazib/Google Drive - UCSC(nssorath)/Study/03_Spring_2020/CSE 210A/Assignments/HW4-WHILE-SS/while-ss/while-ss"

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = "/Users/nazib/Google Drive - UCSC(nssorath)/Study/03_Spring_2020/CSE 210A/Assignments/HW4-WHILE-SS/while-ss/while-ss/cmake-build-debug"

# Include any dependencies generated for this target.
include CMakeFiles/while_ss.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/while_ss.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/while_ss.dir/flags.make

CMakeFiles/while_ss.dir/main.cpp.o: CMakeFiles/while_ss.dir/flags.make
CMakeFiles/while_ss.dir/main.cpp.o: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="/Users/nazib/Google Drive - UCSC(nssorath)/Study/03_Spring_2020/CSE 210A/Assignments/HW4-WHILE-SS/while-ss/while-ss/cmake-build-debug/CMakeFiles" --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/while_ss.dir/main.cpp.o"
	/Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/while_ss.dir/main.cpp.o -c "/Users/nazib/Google Drive - UCSC(nssorath)/Study/03_Spring_2020/CSE 210A/Assignments/HW4-WHILE-SS/while-ss/while-ss/main.cpp"

CMakeFiles/while_ss.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/while_ss.dir/main.cpp.i"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "/Users/nazib/Google Drive - UCSC(nssorath)/Study/03_Spring_2020/CSE 210A/Assignments/HW4-WHILE-SS/while-ss/while-ss/main.cpp" > CMakeFiles/while_ss.dir/main.cpp.i

CMakeFiles/while_ss.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/while_ss.dir/main.cpp.s"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S "/Users/nazib/Google Drive - UCSC(nssorath)/Study/03_Spring_2020/CSE 210A/Assignments/HW4-WHILE-SS/while-ss/while-ss/main.cpp" -o CMakeFiles/while_ss.dir/main.cpp.s

# Object files for target while_ss
while_ss_OBJECTS = \
"CMakeFiles/while_ss.dir/main.cpp.o"

# External object files for target while_ss
while_ss_EXTERNAL_OBJECTS =

while_ss: CMakeFiles/while_ss.dir/main.cpp.o
while_ss: CMakeFiles/while_ss.dir/build.make
while_ss: CMakeFiles/while_ss.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir="/Users/nazib/Google Drive - UCSC(nssorath)/Study/03_Spring_2020/CSE 210A/Assignments/HW4-WHILE-SS/while-ss/while-ss/cmake-build-debug/CMakeFiles" --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable while_ss"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/while_ss.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/while_ss.dir/build: while_ss

.PHONY : CMakeFiles/while_ss.dir/build

CMakeFiles/while_ss.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/while_ss.dir/cmake_clean.cmake
.PHONY : CMakeFiles/while_ss.dir/clean

CMakeFiles/while_ss.dir/depend:
	cd "/Users/nazib/Google Drive - UCSC(nssorath)/Study/03_Spring_2020/CSE 210A/Assignments/HW4-WHILE-SS/while-ss/while-ss/cmake-build-debug" && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" "/Users/nazib/Google Drive - UCSC(nssorath)/Study/03_Spring_2020/CSE 210A/Assignments/HW4-WHILE-SS/while-ss/while-ss" "/Users/nazib/Google Drive - UCSC(nssorath)/Study/03_Spring_2020/CSE 210A/Assignments/HW4-WHILE-SS/while-ss/while-ss" "/Users/nazib/Google Drive - UCSC(nssorath)/Study/03_Spring_2020/CSE 210A/Assignments/HW4-WHILE-SS/while-ss/while-ss/cmake-build-debug" "/Users/nazib/Google Drive - UCSC(nssorath)/Study/03_Spring_2020/CSE 210A/Assignments/HW4-WHILE-SS/while-ss/while-ss/cmake-build-debug" "/Users/nazib/Google Drive - UCSC(nssorath)/Study/03_Spring_2020/CSE 210A/Assignments/HW4-WHILE-SS/while-ss/while-ss/cmake-build-debug/CMakeFiles/while_ss.dir/DependInfo.cmake" --color=$(COLOR)
.PHONY : CMakeFiles/while_ss.dir/depend
