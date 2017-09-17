input_path = input("Enter the directory path containing profiles to be parsed: ")
output_path = input("Enter the directory path where reports will be dumped: ")
filters = input("Profile properties for which reports will be generated (optional): ")
print(input_path)
print(output_path)
print(filters)

# execute maven package command on cmd
# redirect cmd output to python stdout
# execute jar file from target dir with parameters using subprocess
# show some message 