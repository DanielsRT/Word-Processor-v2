import java.util.*;
import java.io.*;
public class WordProc_v2 {
    
    static String MENU = "\n(N)ew File, (O)pen file, (S)ave file, (A)dd lines to end,\n" +
                         "(R)eplace a single line, (I)nsert lines, (D)elete a line,\n" +
                         "(Q)uit program\n";
    static Scanner keyb = new Scanner(System.in);
    static String FILE_NAME = "";
    static Boolean unsavedChanges = false;
    
    public static void main(String[] args) {
        ArrayList<String> lineList = new ArrayList<>();
        char userChoice;
        
        do {
            showTheListElements(lineList);
            userChoice = getChoice();
            
            switch (userChoice) {
                case 'N':
                    lineList = newFile(lineList);
                    break;
                case 'O':
                    lineList = openFile(lineList);
                    break;
                case 'S':
                    saveFile(lineList);
                    break;
                case 'A':
                    addLine(lineList);
                    break;
                case 'R':
                    replaceLine(lineList);
                    break;
                case 'I':
                    insertLines(lineList);
                    break;
                case 'D':
                    deleteLine(lineList);
                    break;
                case 'Q':
                    quitProgram(lineList);
                    break;
                default:
                    System.out.println("No such option");
            }
        } while (userChoice != 'Q');   
    }
    
    // saveWhenUnsaved(lineList)
    static void saveWhenUnsaved(ArrayList<String> lineList) {
        if (FILE_NAME.equals("")) {
            System.out.print("Enter a file name: ");
            FILE_NAME = keyb.nextLine();
            try (PrintWriter pw = new PrintWriter(FILE_NAME)) {
                for (int ndx = 0; ndx < lineList.size(); ndx++) {
                pw.println(lineList.get(ndx));
                }
                System.out.printf("Saved %d lines to '%s'\n",
                                 lineList.size(), FILE_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try (PrintWriter pw = new PrintWriter(FILE_NAME)) {
                for (int ndx = 0; ndx < lineList.size(); ndx++) {
                pw.println(lineList.get(ndx));
                }
                System.out.printf("Saved %d lines to '%s'\n",
                                 lineList.size(), FILE_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        unsavedChanges = false;
    }
    
    // countList(lineList)
    static int countList(ArrayList<String> lineList) {
        int count = 0;
        for (int ndx = 0; ndx < lineList.size(); ndx++) {
            count++;
        }
        count -= 1;
        return count;
    }
    
    // confirmSave(lineList)
    static void confirmSave(ArrayList<String> lineList) {
        if (unsavedChanges == true) {
            System.out.print("There are unsaved changes. Save? (Y/N): ");
            String saveStatus = keyb.nextLine().toUpperCase();
            int count = 0;
            while (count == 0) {
               if (saveStatus.equals("Y")) {
                   saveWhenUnsaved(lineList);
                   count++;
               } else if (saveStatus.equals("N")) {
                    count++;
               } else {
                    System.out.println("No such option");
               } 
            }  
        }
    }
    
    // newFile(lineList)
    static ArrayList<String> newFile(ArrayList<String> lineList) {
        confirmSave(lineList);
        unsavedChanges = false;
        lineList.clear();
        return lineList;
    }
    
    // openFile()
    static ArrayList<String> openFile(ArrayList<String> lineList) {
        confirmSave(lineList);
        unsavedChanges = false;
        System.out.print("Enter a file name to open: ");
        FILE_NAME = keyb.nextLine();
        ArrayList<String> openedList = new ArrayList<>();
        try (Scanner fileScan = new Scanner(new File(FILE_NAME))) {
            while (fileScan.hasNext()) {
                String line = fileScan.nextLine();
                openedList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return openedList;
    }
    
    // saveFile(lineList)
    static void saveFile(ArrayList<String> lineList) {
        unsavedChanges = false;
        System.out.print("Enter a file name: ");
        FILE_NAME = keyb.nextLine();
        try (PrintWriter pw = new PrintWriter(FILE_NAME)) {
            for (int ndx = 0; ndx < lineList.size(); ndx++) {
                pw.println(lineList.get(ndx));
            }
            System.out.printf("Saved %d lines to '%s'\n",
                                 lineList.size(), FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    // addLine(lineList)
    static void addLine(ArrayList<String> lineList) {
        unsavedChanges = true;
        System.out.println("\nEnter lines, type STOP on a line by itself when done");
        String lineToAdd = "";
        while (!lineToAdd.equals("STOP")) {
            System.out.print("==> ");
            lineToAdd = keyb.nextLine();
            if (!lineToAdd.equals("STOP")) {
               lineList.add(lineToAdd); 
            }
        }
    }
    
    // replaceLine(lineList)
    static void replaceLine(ArrayList<String> lineList) {
        unsavedChanges = true;
        System.out.print("What line do you want to replace? ");
        int index = Integer.parseInt(keyb.nextLine());
        System.out.println("Enter a line to replace with:");
        System.out.print("==> ");
        String lineToAdd = keyb.nextLine();
        lineList.remove(index);
        lineList.add(index, lineToAdd);
    }
    
    // insertLines(lineList)
    static void insertLines(ArrayList<String> lineList) {
        unsavedChanges = true;
        int lineListCount = countList(lineList);
        System.out.printf("Enter a line index between 0 and %d: ", lineListCount);
        int index = Integer.parseInt(keyb.nextLine());
        System.out.println("Enter lines to insert, type STOP on a line by itself when done");
        String lineToAdd = "";
        while (!lineToAdd.equals("STOP")) {
            System.out.print("==> ");
            lineToAdd = keyb.nextLine();
            if (!lineToAdd.equals("STOP")) {
               lineList.add(index, lineToAdd);
               index++;
            }
        }
    }
    
    // deleteLine(lineList)
    static void deleteLine(ArrayList<String> lineList) {
        unsavedChanges = true;
        System.out.print("Which line do you want to delete? ");
        int elementNumber = Integer.parseInt(keyb.nextLine());
        lineList.remove(elementNumber);
    }
    
    // quitProgram(lineList)
    static void quitProgram(ArrayList<String> lineList) {
        confirmSave(lineList);
        System.out.println("Bye");
    }
    
    // showTheListElements(lineList)
    static void showTheListElements(ArrayList<String> lineList) {
        if (unsavedChanges == true) {
            System.out.printf("\nFile: [%s] unsaved changes\n", FILE_NAME);
        } else {
            System.out.printf("\nFile: [%s] saved\n", FILE_NAME);
        }
        System.out.println("Index   Line");
        System.out.println("=====   ==============================");
        for (int ndx = 0; ndx < lineList.size(); ndx++) {
            System.out.printf("%3d:    %s\n", ndx, lineList.get(ndx));
        }
    }
 
    // userChoice = getChoice()
    static char getChoice() {
        System.out.print(MENU);
        System.out.print("Enter your choice: ");
        String user_input = keyb.nextLine().toUpperCase();
        return user_input.charAt(0);
    }
    
}