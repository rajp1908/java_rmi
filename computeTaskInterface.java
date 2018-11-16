import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedHashSet;

public interface computeTaskInterface extends Remote{
	public String addCourse(String courseID,String sem) throws RemoteException;
	public String removeCourse(String courseID,String sem) throws RemoteException;
	public String listCourseAvailability(String sem) throws RemoteException;
	public String enrollCourse(String studentID,String courseID,String sem) throws RemoteException;
	public HashMap<String, LinkedHashSet<String>> getClassSchedule(String studentID) throws RemoteException;
	public String dropCourse(String studentID,String courseID) throws RemoteException;
	public String check(String id, char c) throws RemoteException;
	public void setClient(String id) throws RemoteException;
	public String getClient() throws RemoteException;
	public void setCapacity(int i) throws RemoteException;
}
