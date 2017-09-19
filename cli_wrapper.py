import subprocess, os

# constants with global scope
INPUT = "--input"
OUTPUT = "--output"
FILTERS = "--filters"
JAR_DIRECTORY = "target"
JAR_NAME = "report-engine.jar"

def build_jar(should_package):
    # check if jar is to be built 
    if len(should_package) != 0 and (should_package[0] == 'Y' or should_package[0] == 'y'):   
        # define build commands for maven  
        mvn_cmd = ['mvn', 'clean', 'package']
        print("\nBuilding " + JAR_NAME + " from src using command:\n" + ' '.join(mvn_cmd) + '\n')
        # build jar using maven through an external process spawned from python
        mvn_process = subprocess.Popen(mvn_cmd, shell=True)
        mvn_process.wait()
        return mvn_process.returncode
    else:
        return None
    
def execute_jar(should_run,app_cmd_args):
    # check if jar is to be run 
    if len(should_run) != 0 and (should_run[0] == 'Y' or should_run[0] == 'y'):     
        # form jar file path based on underlying os
        jar_location = os.path.join(JAR_DIRECTORY,JAR_NAME)
        # define commands for executing .jar file using java
        jar_cmd = ['java', '-jar',  jar_location]
        # parse arguments
        for key,value in app_cmd_args.items():
            jar_cmd.append(key + '=' + value)
        print("\nExecuting " + JAR_NAME + " using command: \n" + ' '.join(jar_cmd) + '\n')
        # execute jar using java through an external process spawned from python
        jar_process = subprocess.Popen(jar_cmd, shell=True)
        jar_process.wait()
        print('\nReport engine finished execution!')
    
def main():
    # input from user through stdin
    input_path = input("Enter the directory path containing profiles to be parsed (--input): ")
    output_path = input("Enter the directory path where reports will be dumped (--output): ")
    filters = input("Profile properties for which reports will be generated (--filters optional): ")
    should_package = input("\nBuild " + JAR_NAME + " file from src (Y/N) ? ")
    # format arguments
    app_cmd_args = dict([(INPUT,input_path), (OUTPUT,output_path)])
    if len(filters) != 0:
        app_cmd_args[FILTERS] = filters
    # validate arguments
    if len(app_cmd_args.get(INPUT)) == 0:
        print("\n" + INPUT + " option is mandatory! Please re-run the cli_wrapper.py script\n")
    else :
        if len(app_cmd_args.get(OUTPUT)) == 0:
            app_cmd_args.pop(OUTPUT)
        # arguments validated successfully
        exit_code = build_jar(should_package)
        # execute .jar file only if maven build is successful
        print("\nMaven exit code: " + str(exit_code)) 
        if exit_code == 0 or exit_code == None:
            should_run = input("\nRun " + JAR_NAME + " file from target (Y/N) ? ")
            execute_jar(should_run,app_cmd_args)

if __name__ == '__main__':
    main()