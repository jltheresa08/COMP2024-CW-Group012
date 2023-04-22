# COMP2024-CW-Group012
This is Group 12's implementation of 3 AI algorithms to solve the Travelling Salesman Problem (TSP).   
Members: Chin Yi Hong, Theresa Lim Jiyi, Khor Sher Liang.
## Getting Started
### Prerequisite
1. Any JAVA IDE. This program was developed using Eclipse and IntelliJ.
2. Any Python IDE for the graph.

### How to run
1. Download this project and unzip the folder.
2. Create a Java project.
3. Delete the generated src folder.    
4. Copy the src folder into that project.
5. Copy TSP_107.txt and graphing.py to outside of src.   
*Note that the structure of files must remain the same to ensure it runs.   
6. For your ease of running, 2 TSP_107.txt have been included into proper folder.

### Or alternatively,
1. Download this project and unzip the folder.
2. Create a Java project. 
3. Open the src folder downloaded from git.     
4. Copy com folder into the generated src folder.
5. Copy TSP_107.txt and graphing.py to outside of src.   
*Note that the structure of files must remain the same to ensure it runs.   
6. For your ease of running, 2 TSP_107.txt have been included into proper folder.

### How to generate graph
1. For ACO, run the code and the graph will be produced automatically.
2. For SA or TS, run the code to generate an output.txt file.
3. Open your python IDE, navigate to your java project folder where graphing.py is and install matplotlib
```
pip install matplotlib
```
4. Run graphing.py. The graph will be generated from there. You may uncomment line 16 to remove the axis of the graph.

## Troubleshooting
1. After installing Matplotlib: Import "matplotlib" could not be resolved from source Pylance(reportMissingModuleSource).<br>
   [Solution](https://stackoverflow.com/questions/70709406/import-matplotlib-could-not-be-resolved-from-source-pylancereportmissingmodul): Refresh your IDE.
2. TSP_107.txt file not found. <br>
   Solution: Check file structure. TSP_107.txt must be OUTSIDE src to run SA and TS. TSP_107.txt must be INSIDE Input folder (src\com\aim\aco\Input).
3. output.txt file not found. <br>
   Solution: Check file structure. output.txt will be generated OUTSIDE src. So graphing.py must be OUTSIDE src to run the graph.

