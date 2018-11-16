import java.io.BufferedWriter;
import java.io.FileWriter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class Client {

	private static Scanner sc;
	private static int info;
	public static void setInfo(int i){
		info = i;
	}
	public static int getInfo(){
		return info;
	}
	public static void addLog(String advisor,String student,String date,String arguments,String status){
		FileWriter fw = null;
		BufferedWriter bw = null;
		String name;
		String str;
		String[] methods = new String[]{"addCourse","removeCourse","listCourseAvailability",
				"enrollCourse","dropCourse","getClassSchedule"};

		try {
			if(!advisor.equals("none")){
				str = advisor;
				name = "./advisor/"+advisor + ".txt";
			}
			else{
				name = "./student/"+student + ".txt";
				str = student;
			}
			fw = new FileWriter(name,true);
			bw = new BufferedWriter(fw);
			bw.write(date + " ");
			bw.write(str + " ");
			if(student.charAt(4) == 'S')
				bw.write(student + " ");
			bw.write(methods[getInfo()-1] + " ");
			bw.write(arguments + " ");
			bw.write(status);
			bw.newLine();				
		}
		catch (Exception e) {}
		finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		
		Registry registry1 = LocateRegistry.getRegistry(2964);
		Registry registry2 = LocateRegistry.getRegistry(2965);
		Registry registry3 = LocateRegistry.getRegistry(2966);
		computeTaskInterface obj1 = (computeTaskInterface)registry1.lookup("COMP");
		computeTaskInterface obj2 = (computeTaskInterface)registry2.lookup("SOEN");
		computeTaskInterface obj3 = (computeTaskInterface)registry3.lookup("INSE");
		String date = "";
		char sr;

		String arguments = "";
		
		while(true){
			System.out.println("Enter your ID : ");
			sc = new Scanner(System.in);
			String str = sc.nextLine();
			String x = str;
			char find = str.charAt(4);
			String sub = str.substring(0,4);
			String student = "";
			boolean isAdvisor = false;
			if(find == 'A'){
				if(sub.equals("COMP")){
					if(obj1.check(x, find).equals("no")){
						System.out.println("ID not found.. Please enter correct ID");
						find = 'x';
					}
					else{
						obj1.setClient(str);
						date = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("E dd/MM/yyyy HH:mm:ss"));
					}
				}
				if(sub.equals("SOEN")){
					if(obj2.check(str, find).equals("no")){
						System.out.println("ID not found.. Please enter correct ID");
						find = 'x';
					}
					else
						obj2.setClient(str);
						date = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("E dd/MM/yyyy HH:mm:ss"));
				}
				if(sub.equals("INSE")){
					if(obj3.check(str, find).equals("no")){
						System.out.println("ID not found.. Please enter correct ID");
						find = 'x';
					}
					else
						obj3.setClient(str);
					date = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("E dd/MM/yyyy HH:mm:ss"));
				}
			}
			String cid,sem,ans = "",status="";
			int i = 0;
			
			if(find=='S'){
				if(sub.equals("COMP")){
					obj1.setClient(str);
					date = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("E dd/MM/yyyy HH:mm:ss"));
					
				}
				if(sub.equals("SOEN")){
					obj2.setClient(str);
					date = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("E dd/MM/yyyy HH:mm:ss"));
				}
				if(sub.equals("INSE")){
					obj3.setClient(str);
					date = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("E dd/MM/yyyy HH:mm:ss"));
				}
				System.out.println("\n1:Enroll course\n2:Drop course\n3:Get Class Schedule\nEnter you choice : ");
				sc = new Scanner(System.in);
				i = sc.nextInt();
				i = i+ 3;
				setInfo(i);
			}
			if(find == 'A'){
				System.out.println("\n1:Add course\n2:Remove course\n3:List course"
				+ "\n4:Enroll course\n5:Drop course\n6:Get Class Schedule\nEnter you choice : ");
				sc = new Scanner(System.in);
				i = sc.nextInt();
				if(i >= 4){
					System.out.println("Enter Student ID : ");
					sc = new Scanner(System.in);
					str = sc.nextLine();
					student = str.substring(0, 4);
					if(student.equals("COMP")){
						if(obj1.check(str, 's').equals("no")){
							System.out.println("ID not found.. Please enter correct ID");
							i = 8;
						}
						else{
							obj1.setClient(str);
							isAdvisor = true;
						}
					}
					if(student.equals("SOEN")){
						if(obj2.check(str, 's').equals("no")){
							System.out.println("ID not found.. Please enter correct ID");
							i = 8;
						}
						else{
							obj2.setClient(str);
							isAdvisor = true;
						}
					}
					if(student.equals("INSE")){
						if(obj3.check(str, 's').equals("no")){
							System.out.println("ID not found.. Please enter correct ID");
							i = 8;
						}
						else{
							obj3.setClient(str);
							isAdvisor = true;
						}
					}
				}
				setInfo(i);
			}
			switch(i){

				case 1: System.out.println("Enter course ID: ");
						sc = new Scanner(System.in);
						cid = sc.nextLine();
						System.out.println("Enter sem : ");
						sc = new Scanner(System.in);
						sem = sc.nextLine();
						System.out.println("Enter capacity : ");
						sc = new Scanner(System.in);
						int capacity = sc.nextInt();
						arguments = "("+cid+", "+sem+")";
						if(sub.equals("COMP")){
							obj1.setCapacity(capacity);
							ans = obj1.addCourse(cid, sem);										
						}
						else if(sub.equals("SOEN")){
							obj2.setCapacity(capacity);
							ans = obj2.addCourse(cid, sem);										
						}
						else if(sub.equals("INSE")){
							obj3.setCapacity(capacity);
							ans = obj3.addCourse(cid, sem);										
						}
						if(ans.equals("done")){
							System.out.println(cid+" added successfully");
							status = "success";
						}
						if(ans.equals("exists")){
							System.out.println(cid+" already exists");
							status = "unsuccessful";
						}
						if(ans.equals("invalid")){
							System.out.println(cid+" is invalid");
							status = "unsuccessful";
						}
						addLog(str,"none",date,arguments,status);
					break; 
				case 2: System.out.println("Enter course ID: ");
						sc = new Scanner(System.in);
						cid = sc.nextLine();
						System.out.println("Enter sem : ");
						sc = new Scanner(System.in);
						sem = sc.nextLine();
						arguments = "("+cid+", "+sem+")";
						if(sub.equals("COMP")){
							ans = obj1.removeCourse(cid, sem);
						}
						else if(sub.equals("SOEN")){
							ans = obj2.removeCourse(cid, sem);
						}
						else if(sub.equals("INSE")){
							ans = obj3.removeCourse(cid, sem);
						}
						if(ans.equals("done")){
							System.out.println(cid+" removed successfully");
							status = "success";
						}
						if(ans.equals("cant")){
							System.out.println(cid +" doesn't exists");
							status = "unsuccessful";
						}
						if(ans.equals("invalid")){
							System.out.println(cid+" is invalid");
							status = "unsuccessful";
						}
						addLog(str,"none",date,arguments,status);
					break;
				case 3: System.out.println("Enter sem : ");
						sc = new Scanner(System.in);
						sem = sc.nextLine();
						arguments = "("+sem+")";
						if(str.substring(0, 4).equals("COMP")){
							ans = obj1.listCourseAvailability(sem);
						}
						if(str.substring(0, 4).equals("SOEN")){
							ans = obj2.listCourseAvailability(sem);
						}
						if(str.substring(0, 4).equals("INSE")){
							ans = obj3.listCourseAvailability(sem);
						}
						if(ans.equals("invalid"))
							status = "unsuccessful";
						else{
							System.out.println(ans);
							status = "success";
						}
						addLog(str,"none",date,arguments,status);
					break;
				case 4: System.out.println("Enter course ID: ");
						sc = new Scanner(System.in);
						cid = sc.nextLine();
						System.out.println("Enter sem : ");
						sc = new Scanner(System.in);
						sem = sc.nextLine();
						arguments = "("+str+", "+cid+", "+sem+")";
						if(sub.equals("COMP")){
							ans = obj1.enrollCourse(str, cid, sem);
						}
						if(sub.equals("SOEN")){
							ans = obj2.enrollCourse(str, cid, sem);
						}
						if(sub.equals("INSE")){
							ans = obj3.enrollCourse(str, cid, sem);
						}
						if(ans.equals("invalid")){
							System.out.println("Invalid ID");
							status = "unsuccessful";
						}
						if(ans.equals("notexists")){
							System.out.println("You have entered wrong courseID");
							status = "unsuccessful";
						}
						if(ans.equals("full")){
							System.out.println("All seats are full");
							status = "unsuccessful";
						}
						if(ans.equals("enrolled") || ans.equals("register3")){
							System.out.println("You have already registered for 3 courses.. Can't take more course");
							status = "unsuccessful";
						}
						if(ans.equals("cant")){
							System.err.println("Can not take more than 2 courses of other department");
							status = "unsuccessful";
						}
						if(ans.equals("taken")){
							System.out.println("Course already been taken");
							status = "unsuccessful";
						}
						if(ans.equals("enroll")){
							System.out.println("Successfully enrolled in "+cid);
							status = "success";
						}
						if(isAdvisor)
							addLog(x,str,date,arguments,status);
						else
							addLog("none",str,date,arguments,status);
					break;
				case 5: System.out.println("Enter course ID: ");
						sc = new Scanner(System.in);
						cid = sc.nextLine();
						arguments = "("+str+", "+cid+")";
						if(sub.equals("COMP"))
							ans = obj1.dropCourse(str,cid);
						else if(sub.equals("SOEN"))
							ans = obj2.dropCourse(str,cid);
						else if(sub.equals("INSE"))
							ans = obj3.dropCourse(str,cid);
						if(ans.equals("invalid")){
							System.out.println("Invalid ID");
							status = "unsuccessful";
						}
						else if(ans.equals("dropped")){
							System.out.println("Course dropped successully");
							status = "success";
						}
						else{
							System.out.println("No such course found");
							status = "unsuccessful";
						}
						if(isAdvisor)
							addLog(x,str,date,arguments,status);
						else
							addLog("none",str,date,arguments,status);
					break;
				case 6: arguments = "("+str+")";
				HashMap<String, LinkedHashSet<String>> hm = new HashMap<>();
						if(sub.equals("COMP")){
							hm = obj1.getClassSchedule(str);
						}
						else if(sub.equals("SOEN"))
							hm = obj2.getClassSchedule(str);
						else if(sub.equals("INSE"))
							hm = obj3.getClassSchedule(str);
						if(hm != null){
							System.out.println("Student has taken courses : ");
							status = "success";
							System.out.println(hm);
						}
						else{
							System.out.println("You have not registered any courses ");
							status = "unsuccessful";
						}
						if(isAdvisor)
							addLog(x,str,date,arguments,status);
						else
							addLog("none",str,date,arguments,status);
					break;
			}
			
			System.out.println("Press 'n' or 'N' to logout\nPress any other key to continue");
			sc = new Scanner(System.in);
			sr = sc.next().charAt(0);
			if(sr == 'n' || sr == 'N')
				break;
		}
		
	}
}