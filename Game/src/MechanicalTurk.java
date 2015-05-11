import java.util.Random;


public class MechanicalTurk implements AIInterface{

	public Action getMove(Board b) {
		int AI = 2;
		Random rand = new Random();
		int move = (int) ((rand.nextInt())%7);
		Action dumb = new Action(AI, move);
		
		System.out.println("HERE");
		
		if (b.isLegal(dumb)){
			move = (int) (rand.nextInt()%7);
			dumb = new Action(AI, move);
		}
		
		return dumb;
	}
}
