<h1> EXPLANATION OF THE CODE </h1>

In the given code, we indicate the number of threads for image processing using either “S” or “M” letter. If the processingMode is:
•	“S”, the program sets the number of threads to 1. Which means that the image processing will occur in a single thread, in a serial fashion, one square after another. The next square is processed only after the previous one is finished. 
•	“M”, the program sets the number of threads to the number of available processors on the machine. This enables parallel processing, where multiple squares of the image are processed at the same time by different threads. 

<h2> RUNNING THE PROGRAM </h2>

<b>Prerequisites:</b> Java Development Kit (JDK) 8 or higher. 

<b>Step 1:</b> Make sure Java is installed in your machine. To check this, enter the java -version command in Terminal. If Java is not installed, you can get it from Oracle website or simply enter brew install openjdk

<b>Step 2:</b> Save the file as “ImageProcessor.java”

<b>Step 3:</b> Open the Terminal and navigate to the directory where the java file is saved. “cd” command can be used to accomplish this. In my case, I navigated to the folder as: cd ~/IdeaProjects/imageProcessing/src

<b>Step 4:</b> Compile the file using this command: javac ImageProcessor.java

<b>Step 5:</b> Run the program using: <b>java ImageProcessor filename.jpg 30 S</b> or <b>java ImageProcessor filename.jpg 30 M</b>

<b>Notes:</b> Please replace the filename with the name of the image file you want to test. 30 written above indicates the square size. The image I tested the program with was large in size, thus, the result was hard to see when I entered numbers like “5”, “10”, or “15”. The bigger the square size is, the blurrier the picture will be, of course, depending on the size of the original image itself. In addition, please make sure the image file is located in the same folder as your java code. 
