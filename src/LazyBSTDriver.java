import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class LazyBSTDriver {
    public static void main(String[] args) {
        Scanner in;
        if(args.length != 2) {
            //check number of arguments
            System.out.println("Error Incorrect Arguments:" + Arrays.toString(args));
            System.exit(0);
        }
        try {
            //initialize input and output files
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
            File outputFile = new File(args[1]);
            PrintWriter out;
            out = new PrintWriter(outputFile);

            //initialize tree
            LazyBinarySearchTree lbst = new LazyBinarySearchTree();

            //operation string used for each line in the input file
            String operation = "";
            
            //variables to hold key values and operation results
            int key;
            boolean result;

            //while there is a next line in the file
            whileloop:
            while(in.hasNextLine()) {
                operation = in.nextLine();
                //split the statement and key value with ":"
                String[] op = operation.split("(?<=:)");
                switch(op[0]) {
                    //if next line is empty, file is done break from loop
                    case "": break whileloop;
                    //checks for insert statement
                    case "Insert:": try {
                                        key = Integer.valueOf(op[1]);
                                        result = lbst.insert(key);
                                        //print result to output file
                                        out.println(result ? "True" : "False");
                                    } catch (IllegalArgumentException e) {
                                        //catches illegal argument exception thrown if key is invalid
                                        out.println("Error in Insert: IllegalArgumentException raised");
                                    }
                                    break;
                    //checks for delete statement
                    case "Delete:": try {
                                        key = Integer.valueOf(op[1]);
                                        result = lbst.delete(key);
                                        //print result to output file
                                        out.println(result ? "True" : "False");
                                    } catch (IllegalArgumentException e) {
                                        //catches illegal argument exception thrown if key is invalid
                                        out.println("Error in Delete: IllegalArgumentException raised");
                                    }
                                    break;
                    //checks for contains statement
                    case "Contains:":   try {
                                            key = Integer.valueOf(op[1]);
                                            result = lbst.contains(key);
                                            //print result to output file
                                            out.println(result ? "True" : "False");
                                        } catch (IllegalArgumentException e) {
                                            //catches illegal argument exception thrown if key is invalid
                                            out.println("Error in Contains: IllegalArgumentException raised");
                                        }
                                        break;
                    //checks for find min statement
                    case "FindMin": int min = lbst.findMin();
                                    out.println(min);
                                    break;
                    //checks for find max statement
                    case "FindMax": int max = lbst.findMax();
                                    out.println(max);
                                    break;
                    //checks for print tree statement
                    case "PrintTree": String tree = lbst.toString();
                                      out.println(tree);
                                      break;
                    //checks for height statement
                    case "Height": int height = lbst.height();
                                   out.println(height);
                                   break;
                    //checks for size statement
                    case "Size": int size = lbst.size();
                                 out.println(size);
                                 break;
                    //if the operation isn't recognized, print an error
                    default: out.println("Error in Line: " + operation);
                             in.nextLine();

                }
            }
            //close the scanner and printwriter
            in.close();
            out.close();
        } catch (Exception e) {
            //catches any exceptions thrown
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
