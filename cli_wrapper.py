import subprocess, os

# constants with global scope
INPUT = "--input"
OUTPUT = "--output"
FILTERS = "--filters"
SUPPPLEMENTS = "--supplements"
JAR_DIRECTORY = "target"
JAR_NAME = "report-engine.jar"

def build_jar():
    should_package = input("\nBuild " + JAR_NAME + " file from src (Y/N) ? ")
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
    should_run = input("\nRun " + JAR_NAME + " file from target (Y/N) ? ")
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
        return jar_process.returncode;
    else:
        return None
    
def main():
    # input from user through stdin
    input_path = input("Enter the directory path containing profiles to be parsed (--input): ")
    output_path = input("Enter the directory path where reports will be dumped (--output): ")
    filters = input("Profile properties for which filtered reports will be generated (--filters optional): ")
    supplements = input("Profile properties for which supplementary reports will be generated (--supplements optional): ")
    
    # format arguments
    app_cmd_args = dict([(INPUT,input_path)])
    
    # validate optional arguments
    if len(filters) != 0:
        app_cmd_args[FILTERS] = filters
    if len(output_path) != 0:
        app_cmd_args[OUTPUT] = output_path
    if len(supplements) != 0:
        app_cmd_args[SUPPPLEMENTS] = supplements
    
    # validate arguments
    if len(app_cmd_args.get(INPUT)) == 0:
        print("\n" + INPUT + " option is mandatory! Please re-run the cli_wrapper.py script\n")
    else :
        # argument validated successfully
        mvn_exit_code = build_jar()
        # execute .jar file only if maven build is successful
        print("\nMaven exit code: " + str(mvn_exit_code)) 
        if mvn_exit_code == 0 or mvn_exit_code == None:
            jar_exit_code = execute_jar(app_cmd_args)
            print("\nJaava exit code: " + str(jar_exit_code))
    print('\nReport engine terminated!')

if __name__ == '__main__':
    main()