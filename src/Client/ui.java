/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;


public class ui {
    
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static String takeGender(BufferedReader reader, String text) {
        String gender = " ";
        String answer;
        try {
            do {
                System.out.println(text + "(m/f)");
                answer = reader.readLine();
                switch (answer) {
                case "M":
                case "m":
                        gender = "Male";
                        break;
                case "F":
                case "f":
                        gender = "Female";
                        break;
                default:
                        System.out.println("The data introduced is NOT correct.");
                        System.out.println("Please introduce m in case of Male or f in case of Female");
                        System.out.println("Try again.");
                        gender = " ";
                        break;
                    }
                } while (gender.equals(" "));
        } catch (IOException ex) {
                System.out.println("Error reading");
        }
        return gender;
	}
    
    public static LocalDate takeDate(BufferedReader reader, String text) {
        boolean check = false;
        String data = "";
        LocalDate day = LocalDate.now();
        System.out.println(text);
        while (!check || data.equals("")) {
                try {
                        data = reader.readLine();
                        day = LocalDate.parse(data, formatter);
                        check = true;
                } catch (IOException ex) {
                        System.out.println("Error reading");
                } catch (DateTimeParseException nfex) {
                        System.out.println("You have not introduced a valid Date. Try again.");
                        System.out.println(text);
                }
        }
	return day;
    }
    
    public static String takeTelephone(BufferedReader reader, String text) {
		String num="reader";
		boolean check = true;
		char cad[];
		try {
                    do {
                        System.out.println(text + " (without spaces)");
                        num = reader.readLine();
                        cad = num.toCharArray();
                        check = false;
                        for (int i = 0; i < num.length(); i++) {

                            if (Character.isDigit(cad[i]) || cad[i] == '+') {

                            } else if (Character.isSpaceChar(cad[i])) {
                                    check = true;
                                    break;
                            } else {
                                    check = true;
                                    break;
                            }
                            if (num.length() != 9){
                                check =true;
                            }
                        }
                        if (check == true) {
                                System.out.println("Invalid telephone number.");
                                System.out.println("Please introduce a 9 digit number.");
                        }
                    } while (check);
                    if (num.substring(0, 1).contains("+")) {
                            num = num.substring(3, num.length());
                    }

		} catch (IOException e) {
			e.printStackTrace();
		}

		return num;

	}
    
    public static String takeDNI(BufferedReader reader, String text) {
		
		String num = "error in takeDNI()";
		boolean check = true;
		char cad[];
		try {
                    do {
                            System.out.println(text + " (without spaces)");
                            num = reader.readLine();
                            cad = num.toCharArray();
                            check = false;
                            for (int i = 0; i < num.length(); i++) {

                                    if (Character.isDigit(cad[i]) || cad[i] == '+') {

                                    } else if (Character.isSpaceChar(cad[i])) {
                                            check = true;
                                            break;
                                    } else {
                                            check = true;
                                            break;
                                    }
                                    if(num.length() != 8 ){
                                        check =true;
                                    }
                            }
                            if (check == true) {
                                    System.out.println("You don't introduce a valid DNI number.");
                                    System.out.println("Please introduce numbers.");
                            }
                    } while (check);
                    if (num.substring(0, 1).contains("+")) {
                            num = num.substring(3, num.length());
                    }

		} catch (IOException e) {
			e.printStackTrace();
		}

		return num;

	}
    
    public static int takeInteger(BufferedReader reader, String text) {
		boolean check = false;
		int data = 0;

		System.out.println(text);

		while (!check || data < 0) {
			try {
				data = Integer.parseInt(reader.readLine());
				check = true;
			} catch (IOException ex) {
				System.out.println("Error reading");
			} catch (NumberFormatException nfex) {
				System.out.println("You have not introduced a Integer, you must do it.");
				System.out.println(text);
			}

		}
		return data;
	}

    public static boolean CheckOption(int num, int max) {
            if (num > max || num < 0) {
                    System.out.println("This number is not an option");
                    System.out.println("The number must be between 1 and " + max);
                    System.out.println("Try again!");
                    return true;
            } else {
                    return false;
            }
	}
    
    public static String takePasswordAndHashIt(BufferedReader reader, String text) {
		System.out.println(text);
		byte[] returnValue = null;
                ArrayList<String> cadena=new ArrayList<String>();
                String hexa=null;
		try {
			String password = reader.readLine();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			returnValue = md.digest();
                        for (byte b: returnValue){
                            cadena.add(Integer.toHexString(0xFF&b));
                        }
                        hexa=String.join("",cadena);
                        
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return hexa;
    }
    
    
    public static boolean areYouSure(BufferedReader reader, String text) {
		boolean resp = false;
		boolean loop = false;
		String answer = "";
		try {
                    do {
                        System.out.println(text + " (yes/no):");
                        answer = reader.readLine();
                        if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no")) {
                                loop = false;
                                if (answer.contains("yes")||answer.contains("Yes")||answer.contains("YES")) {
                                        resp = true;
                                } else {
                                        resp = false;
                                }
                        } else {
                                System.out.println("The answer is not correct. Try again.");
                                loop = true;
                        }
			} while (loop);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return resp;
	}
    

}
