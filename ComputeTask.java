import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

class ClientDetails{
	String id;
	
}

class StudentDetails{
			
	List<String> students_co = new ArrayList<>();
	List<String> students_so = new ArrayList<>();
	List<String> students_in = new ArrayList<>();
	
	
	List<String> advisor_co = new ArrayList<>();
	List<String> advisor_so = new ArrayList<>();
	List<String> advisor_in = new ArrayList<>();
	
	public StudentDetails(){
	}
	
	public StudentDetails(int i){
		
		students_co.add("COMPS001");
		students_co.add("COMPS002");
		students_co.add("COMPS003");
		
		students_so.add("SOENS001");
		students_so.add("SOENS002");
		students_so.add("SOENS003");
		
		students_in.add("INSES001");
		students_in.add("INSES002");
		students_in.add("INSES003");
		
		advisor_co.add("COMPA1");
		advisor_so.add("SOENA1");
		advisor_in.add("INSEA1");
	}
}

public class ComputeTask extends UnicastRemoteObject implements computeTaskInterface{
	private boolean msg;
	private int capacity;
	private static final long serialVersionUID = 1L;
	private String client;
	StudentDetails std = new StudentDetails(1);
	Server1 s1 = new Server1();
	Server2 s2 = new Server2();
	Server3 s3 = new Server3();

	private String sem;
	protected ComputeTask() throws RemoteException {
		super();
	}
	@Override
	
	public void setClient(String id) throws RemoteException{
		this.client = id;
	}
	public String getClient() throws RemoteException{
		return client;
	}
	public void setCapacity(int i) throws RemoteException {
		this.capacity = i;
	}
	public int getCapacity() {
		return capacity;
	}
	@Override
	public String check(String id,char c) throws RemoteException {
		if(c == 'A'){
			System.out.println(id);
			if(std.advisor_co.contains(id))
				return "yes";
			if(std.advisor_so.contains(id))
				return "yes";
			if(std.advisor_in.contains(id))
				return "yes";
			return "no";
		}
		else if(c == 'S'){
			System.out.println(id);
			System.out.println(std.students_co.contains(id));
			if(std.students_co.contains(id))
				return "yes";
			if(std.students_so.contains(id))
				return "yes";
			if(std.students_in.contains(id))
				return "yes";
			return "no";
		}
		return "no";
	}
	
	@Override
	public synchronized String addCourse(String courseID, String sem) throws RemoteException {
		String ser = courseID.substring(0,4).toLowerCase();
		String ans = "invalid";
		if(ser.equals("comp")){
			ans = s1.addCourse_comp(courseID, sem,getCapacity());
		}
		if(ser.equals("soen")){
			ans = s2.addCourse_soen(courseID, sem,getCapacity());
		}
		if(ser.equals("inse"))
			ans = s3.addCourse_inse(courseID, sem,getCapacity());
		return ans;
	}
	@Override
	public synchronized String removeCourse(String courseID, String sem) throws RemoteException {
		String advisor = getClient();
		String ans = "invalid";
		if(advisor.substring(0, 4).equals("COMP")){
			ans = s1.remove(courseID,sem);
		}
		if(advisor.substring(0, 4).equals("SOEN")){
			ans = s2.remove(courseID,sem);
		}
		if(advisor.substring(0, 4).equals("INSE")){
			ans = s3.remove(courseID,sem);
		}
		return ans;		
	}

	@Override
	public synchronized String listCourseAvailability(String sem) throws RemoteException {
		String clt = getClient();
		String ans = "invalid";
		if(clt.subSequence(0, 4).equals("COMP")){
			ans = s1.listCourse(sem);
		}
		if(clt.subSequence(0, 4).equals("SOEN")){
			ans = s2.listCourse(sem);
		}
		if(clt.subSequence(0, 4).equals("INSE")){
			ans = s3.listCourse(sem);
		}
		return ans;
	}

	@Override
	public synchronized String enrollCourse(String studentID, String courseID, String sem) throws RemoteException {
		String course = studentID.substring(0,4).toLowerCase();
		System.out.println(course);
		String en = "";
		if(course.equals("comp"))
			en = s1.enroll_comp(studentID,courseID,sem);
		if(course.equals("soen"))
			en = s2.enroll_soen(studentID,courseID,sem);
		if(course.equals("inse"))
			en = s3.enroll_inse(studentID,courseID,sem);
		System.out.println(en);
		return en;
	}

	@Override
	public synchronized HashMap<String, LinkedHashSet<String>> getClassSchedule(String studentID) throws RemoteException {
		
		HashMap<String, LinkedHashSet<String>> hm = new HashMap<>(); 
		String advisor = getClient();
		if(studentID.substring(0, 4).equals(advisor.subSequence(0, 4))){
			if(studentID.substring(0, 4).equals("COMP")){
				hm = s1.getschedule(studentID);
			}
			if(studentID.substring(0, 4).equals("SOEN")){
				hm = s2.getschedule(studentID);
			}
			if(studentID.substring(0, 4).equals("INSE")){
				hm = s3.getschedule(studentID);
			}
		}
		return hm;
	}

	@Override
	public synchronized String dropCourse(String studentID, String courseID) throws RemoteException {
		
		String student = studentID.substring(0, 4);
		String advisor = getClient().substring(0, 4);
		String ans = "";
		if(student.equals("COMP") && advisor.equals("COMP"))
		{
			ans = s1.drop(studentID,courseID);
		}
		if(student.equals("SOEN") && advisor.equals("SOEN"))
		{
			ans = s2.drop(studentID,courseID);
		}
		if(student.equals("INSE") && advisor.equals("INSE"))
		{
			ans = s3.drop(studentID,courseID);
		}
		return ans;		
	}
	public boolean isMsg() {
		return msg;
	}
	public void setMsg(boolean msg) {
		this.msg = msg;
	}
	public String getSem() {
		return sem;
	}
	public void setSem(String sem) {
		this.sem = sem;
	}	
}
