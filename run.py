import subprocess

input_path = input("Enter the directory path containing profiles to be parsed: ")
output_path = input("Enter the directory path where reports will be dumped: ")
filters = input("Profile properties for which reports will be generated (optional): ")
build_jar = input("Build .jar file from src (Y/N) ? ")

print('\n')

app_cmd_args = dict([('--input',input_path), ('--output',output_path)])
if len(filters) != 0:
    app_cmd_args['--filters'] = filters

if len(build_jar) != 0 and (build_jar[0] == 'Y' or build_jar[0] == 'y'):
    mvn_cmd = ['mvn', 'clean', 'package']
    print("Building .jar from src using command: " + ' '.join(mvn_cmd) + '\n')
    mvn_process = subprocess.Popen(mvn_cmd, shell=True)
    mvn_process.wait()
    print('\n')

jar_cmd = ['java', '-jar', 'target\\report-engine.jar']
for key,value in app_cmd_args.items():
    jar_cmd.append(key + '=' + value)

print("Executing .jar using command: " + ' '.join(jar_cmd) + '\n')

jar_process = subprocess.Popen(jar_cmd, shell=True)
jar_process.wait()

print('\nReport engine finished execution!')