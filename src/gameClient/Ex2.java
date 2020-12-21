package gameClient;

public class Ex2 {

	public static void main(String[] args) throws InterruptedException {
		Thread client = new Thread(new Ex2_Client());
		client.start();
	}

}


