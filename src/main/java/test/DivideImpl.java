package test;

public class DivideImpl implements Divide {

	public int divide(int a, int b) throws Exception{
		if(b == 0){
			throw new Exception("divide 0 error");
		}
		return a / b;
	}
}
