import java.io.*;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.lang.ArrayIndexOutOfBoundsException;
public class Assign3{
    static String coord="";//a String used for printing the coordinates
    public static void main(String [] args) throws IOException{
        String fileName=null;
        Scanner reader=null;
        Scanner inScan = new Scanner(System.in);
        try{
            if(args[0]!=null){//if a file is given at command line, reader is set up to read it
                fileName=args[0];
                reader=new Scanner(new FileReader(args[0]));
            }
        }
        catch (ArrayIndexOutOfBoundsException e){//no file is given and user input is taken to find the file name. repeats until filename entered
            while (true){
                try{
                    System.out.println("Please enter grid filename:");
                    fileName = inScan.nextLine();
                    reader = new Scanner(new FileReader(fileName));
              
                    break;
                }
                catch (IOException f){
                    System.out.println("Problem " + f);
                }
            }
        }
        new Assign3(fileName);
    }
    
    public Assign3(String file)throws IOException{
        String fileName=file;//assigns file to fileName
        Scanner reader=new Scanner(new FileReader(fileName));//creates a scanner to read the file
        Scanner input=new Scanner(System.in);//creates a scanner for user input
        char[][] letters;//a double character array for the letters
        int rows=0; //number of rows
        int columns=0; //number of columns
        
        try{
            rows=reader.nextInt();//reads in first number for rows
        }
        catch(NoSuchElementException e){
            System.exit(0);
        }
        try{
            columns=reader.nextInt();//reads in second number for columns
        }
        catch(NoSuchElementException e){
            System.exit(0);
        }
        letters=new char[rows][columns];//creates an array with x rows and y columns
        //fills in the array, exits the program if the file is wrong
        for(int i=0;i<rows;i++){
            String temp=reader.next();
            for(int j=0;j<columns;j++){
                try{
                    letters[i][j]=temp.charAt(j);
                }
                catch(NoSuchElementException e){
                    System.exit(0);
                }
            }
        }
        //prints the array
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                System.out.print(letters[i][j]+" ");
            }
            System.out.print("\n");
        }
        System.out.println("\n\nPlease enter the phrase to search for:");
        String phrase = (input.nextLine()).toLowerCase();
        while (!(phrase.equals(""))){//checks if phrase is empty
            System.out.println("Looking for: "+phrase);
            String[] words=phrase.split("\\s+");//splits user input into array based on whitespace
            int numphrases=words.length;
            System.out.println("(contains "+numphrases+" words)");
            boolean found = false;
            //rotates through array
			for (int r = 0; (r < rows && !found); r++)
			{
				for (int c = 0; (c < columns && !found); c++)
				{
				    coord="";
					// Start search for phrase, starting at index 0
					for(int d=0;d<4;d++){
					   found = findPhrase(words, r, c, 0, letters, d);
					   if(found){
					       break;
					   }
					   
				    }   
					
				}
			}
            if(found){//when phrase is found
                System.out.println(getCoord());//prints the coordinates
                coord="";
                for(int i=0;i<rows;i++){
                    for(int j=0;j<columns;j++){
                        System.out.print(letters[i][j]+" ");//prints array
                        letters[i][j] = Character.toLowerCase(letters[i][j]);//lowercases array
                    }
                    System.out.print("\n");
                }
            }
            else{//when phrase isn't found
                System.out.println("phrase not found.");
            }
            gridClear(letters);
            System.out.println("\n\nPlease enter the phrase to search for:");
            phrase = (input.nextLine()).toLowerCase();
        }
        
        
    }
    
    //Takes a string array, an x and y coordinate, an index in the array, a double array to act as a grid, and a direction
    public static boolean findPhrase(String[] phrase, int x, int y, int index, char[][] grid, int direction){
        if(index>phrase.length-1){//checks if past the array's length
            return true;
        }
        else if(x>=grid.length||x<0||y>=grid[0].length||y<0){//checks if out of bounds
            return false;
        }
        else if(grid[x][y]!=phrase[index].charAt(0)){  // char does not match
			return false;
        }
        else{  	// current character matches
		
            
				
			boolean answer=true;
			int j=0;
			int k;
			int x2=x;
			int y2=y;
			if(phrase.length==0){		// empty phrase
				if(index==phrase[index].length()-1){
				    answer = true;	
				}
				else{
				    answer = findPhrase(phrase, x, y+1, index+1, grid, 0);  // Right
				    if (!answer)
				        answer = findPhrase(phrase, x+1, y, index+1, grid, 1);  // Down
				    if (!answer)
				        answer = findPhrase(phrase,x, y-1, index+1, grid, 2);  // Left
				    if (!answer)
				        answer = findPhrase(phrase,x-1, y, index+1, grid, 3); 
				}
            }
				
			else{	//recursion through phrase
			    boolean testingindex=true;
			    
				// Try all four directions if necessary.
				while(testingindex){
				    //checks right, down, left, up, each letter and then the next word
				    if(direction==0){
				        for(j=y;(j-y)<phrase[index].length();j++){
				            if(Character.isUpperCase(grid[x][j])){
				                answer=false;
				                for(k=y;k<=j-1&&!(k>=grid[0].length);k++){
				                    grid[x][k] = Character.toLowerCase(grid[x][k]);
				                }
				                break;
				            }
				        
				            if(index>=1&&((j>=(1+y))&&!(j-2>=grid[0].length||j-2<0))&&!(Character.isUpperCase(grid[x][j-2]))){
				                answer=false;
				                for(k=y;k<=j&&!(k>=grid[0].length);k++){
				                    grid[x][k] = Character.toLowerCase(grid[x][k]);
				                }
				                break;
				            }
				        
				            if((x>=grid.length||x<0||j>=grid[0].length||j<0)||grid[x][j]!=phrase[index].charAt(j-y)){//if letter doesn't match
				                answer=false;
				                if(index==0){
				                    gridClear(grid);
				                }
				                else{
				                    for(k=y;k<=j&&!(k>=grid[0].length);k++){
				                        grid[x][k] = Character.toLowerCase(grid[x][k]);
				                    }
				                    
				                }
				                //grid[x][y] = Character.toUpperCase(grid[x][y]);
				            
				                break;
				            }
				            y2=j;
				            grid[x][j] = Character.toUpperCase(grid[x][j]);
				        }
				    
				        if(answer){
				            answer = findPhrase(phrase, x, (j-1)+1, index+1, grid,0);  // Right
				            if (!answer){
				                answer = findPhrase(phrase, x+1, (j-1), index+1, grid,1); // Down
				            }
				            if (!answer){
				                answer = findPhrase(phrase, x, (j-1)-1, index+1, grid,2); // Left
				            }
				            if (!answer){
				                answer = findPhrase(phrase, x-1, (j-1), index+1, grid,3); //Up
				            }
				            if(!answer){
				                for(k=y;k<=j&&!(k>=grid[0].length);k++){
				                    grid[x][k] = Character.toLowerCase(grid[x][k]); //clears most recent uppercases
				                }
				                y2=y;
				            }
				        
				        }
				    }
				    if(direction==1){
				        answer=true;
				        for(j=x;j-x<phrase[index].length();j++){
				            if((j>=grid.length||j<0||y>=grid[0].length||y<0)||Character.isUpperCase(grid[j][y])){
				                answer=false;
				                for(k=y;k<=j-1&&!(k>=grid[0].length);k++){
				                    grid[k][y] = Character.toLowerCase(grid[k][y]);
				                }
				                break;
				            }
				            
				            if(index>=1&&((j>=(1+x))&&!(j-2>=grid.length||j-2<0))&&!(Character.isUpperCase(grid[j-2][y]))){
				                answer=false;
				                for(k=x;k<=j&&!(k>=grid.length);k++){
				                    grid[k][y] = Character.toLowerCase(grid[k][y]);
				                }
				                break;
				            }
				            
				            if((j>=grid.length||j<0||y>=grid[0].length||y<0)||(grid[j][y]!=phrase[index].charAt(j-x))){
				                answer=false;
				                if(index==0){
				                    gridClear(grid);
				                }
				                else{
				                    for(k=x;k<=j&&!(k>=grid.length);k++){
				                        grid[k][y] = Character.toLowerCase(grid[k][y]);
				                    }
				                    
				                }
				                //grid[x][y] = Character.toUpperCase(grid[x][y]);
				                
				                break;
				            }
				            x2=j;
				            grid[j][y] = Character.toUpperCase(grid[j][y]);
				        }
			        
					    if(answer){
					        answer = findPhrase(phrase,(j-1), y+1, index+1, grid,0);  // Right
					        if (!answer){
					            answer = findPhrase(phrase, (j-1)+1, y, index+1, grid,1); //down
					        }// Down
					        if (!answer){
					           answer = findPhrase(phrase, (j-1), y-1, index+1, grid,2);  // Left
					        }
					        if (!answer){
					           answer = findPhrase(phrase, (j-1)-1, y, index+1, grid,3); //up
					        }
				            if(!answer){
				                x2=x;
				                for(k=x;k<=j&&!(k>=grid.length);k++){
				                        grid[k][y] = Character.toLowerCase(grid[k][y]);
				                    }
				            }
				        }
				    }
				    if(direction==2){
				        
				        answer=true;
				        for(j=y;y-j<phrase[index].length();j--){
				            if((x>=grid.length||x<0||j>=grid[0].length||j<0)||Character.isUpperCase(grid[x][j])){
				                answer=false;
				                for(k=y;k>=j+1&&!(k<0);k--){
				                    grid[x][k] = Character.toLowerCase(grid[x][k]);
				                }
				                break;
				            }
				            if(index>=1&&((j<=(y-1))&&!(j+2>=grid[0].length||j+2<0))&&!(Character.isUpperCase(grid[x][j+2]))){
				                answer=false;
				                for(k=y;k>=j&&!(k<0);k--){
				                    grid[x][k] = Character.toLowerCase(grid[x][k]);
				                }
				                break;
				            }
				            if((x>=grid.length||x<0||j>=grid[0].length||j<0)||(grid[x][j]!=phrase[index].charAt(y-j))){
				                answer=false;
				                if(index==0){
				                    gridClear(grid);
				                }
				                else{
				                    for(k=y;k>=j&&!(k<0);k--){
				                        grid[x][k] = Character.toLowerCase(grid[x][k]);
				                    }
				                    
				                }
				                //grid[x][y] = Character.toUpperCase(grid[x][y]);
				                
				                break;
				            }
				            y2=j;
				            grid[x][j] = Character.toUpperCase(grid[x][j]);
				        }
				        if(answer){
				            answer = findPhrase(phrase, x, (j+1)+1, index+1, grid,0);  // Right
				        
				            if (!answer){
				                answer = findPhrase(phrase, x+1, (j+1), index+1, grid,1);  // Down
				            }
				            if (!answer){
				                answer = findPhrase(phrase, x, (j+1)-1, index+1, grid,2);  // Left
				            }
				            if (!answer){
				                answer = findPhrase(phrase, x-1, (j+1), index+1, grid,3); //Up
				            }
				            if(!answer){
				                y2=y;
				                for(k=y;k>=j&&!(k<0);k--){
				                    grid[x][k] = Character.toLowerCase(grid[x][k]);
				                }
				            }
				        }
				    }
				    if(direction==3){
				        answer=true;
				        for(j=x;x-j<phrase[index].length();j--){
				            if((j>=grid.length||j<0||y>=grid[0].length||y<0)||Character.isUpperCase(grid[j][y])){
				                answer=false;
				                for(k=x;k>=j+1&&!(k<0);k--){
				                    grid[k][y] = Character.toLowerCase(grid[k][y]);
				                }
				                break;
				            }
				            if(index>=1&&((j<=(x-1))&&!(j+2>=grid.length||j+2<0))&&!(Character.isUpperCase(grid[j+2][y]))){
				                answer=false;
				                for(k=x;k>=j&&!(k<0);k--){
				                    grid[k][y] = Character.toLowerCase(grid[k][y]);
				                }
				                break;
				            }
				            if((j>=grid.length||j<0||y>=grid[0].length||y<0)||(grid[j][y]!=phrase[index].charAt(x-j))){
				                answer=false;
				                if(index==0){
				                    gridClear(grid);
				                }
				                else{
				                    for(k=x;k>=j&&!(k<0);k--){
				                        grid[k][y] = Character.toLowerCase(grid[k][y]);
				                    }
				                    
				                }
				                //grid[x][y] = Character.toUpperCase(grid[x][y]);
				                
				                break;
				            }
				            x2=j;
				            grid[j][y] = Character.toUpperCase(grid[j][y]);
				        }
				        if(answer){
				            answer = findPhrase(phrase, (j+1), y+1, index+1, grid,0);  // Right
				            if (!answer){
				                answer = findPhrase(phrase, (j+1)+1, y, index+1, grid,1);  // Down
				            }
				            if (!answer){
				                answer = findPhrase(phrase, (j+1), y-1, index+1, grid,2);  // Left
				            }
				            if (!answer){
				                answer = findPhrase(phrase, (j+1)-1, y, index+1, grid,3); //Up
				            }
				            if(!answer){
				                x2=x;
				                for(k=x;k>=j&&!(k<0);k--){
				                    grid[k][y] = Character.toLowerCase(grid[k][y]);
				                }
				            }
				        }
				    }
				    /*if (!answer){
				        grid[x][y] = Character.toLowerCase(grid[x][y]);
				    }*/
				    
				    testingindex=false;
               }
			}
			if(answer&&(x2!=x)&&j>=x){//If went up
			    String temp=getCoord();
			    storeCoord(phrase[index]+": ("+x+","+y+") to ("+(j-1)+","+y+")\n"+temp);
			    //System.out.println(phrase[index]+": ("+x+","+y+") to ("+(j-1)+","+y+")");
			 }
			else if(answer&&(x2!=x)&&j<=x){//If went down
			    String temp=getCoord();
			    storeCoord(phrase[index]+": ("+x+","+y+") to ("+(j+1)+","+y+")\n"+temp);
			    //System.out.println(phrase[index]+": ("+x+","+y+") to ("+(j+1)+","+y+")");
			}
			else if(answer&&(y2!=y)&&j>=y){//If went right
			    String temp=getCoord();
			    storeCoord(phrase[index]+": ("+x+","+y+") to ("+x+","+(j-1)+")\n"+temp);
			     //System.out.println(phrase[index]+": ("+x+","+y+") to ("+x+","+(j-1)+")");
			}
			else if(answer&&(y2!=y)&&j<=y){//If went left
			    String temp=getCoord();
			    storeCoord(phrase[index]+": ("+x+","+y+") to ("+x+","+(j+1)+")\n"+temp);
			     //System.out.println(phrase[index]+": ("+x+","+y+") to ("+x+","+(j+1)+")");
			}
			else if(answer){//If single character
			    String temp=getCoord();
			    storeCoord(phrase[index]+": ("+x+","+y+") to ("+x+","+y+")\n"+temp);
			}
			return answer;
		}
    }
    public static void gridClear(char[][] grid){//Grid clear
        for(int i=0;i<grid.length-1;i++){
            for(int j=0;j<grid[i].length-1;j++){
                grid[i][j] = Character.toLowerCase(grid[i][j]);
            }                
        }
    }
    public static void storeCoord(String str){//saves a string
        coord=str;
    }
    public static String getCoord(){//recalls that stri
        return coord;
    }
}

