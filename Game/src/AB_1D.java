import java.util.PriorityQueue;


public class AB_1D implements MechanicalTurkInterface{

	@Override
	public Action getTurkMove(BackendBoard backendBoard) {
		PriorityQueue<State> q = new PriorityQueue<State>();
		
		State s = new State(backendBoard);
		
		int x = 0;
		State bestState = null;
		while (q.size() > 0){
			bestState = q.poll();
			
			x++;
			if (x == 1){
				break;
			}
			
			for (int i=0; i < 7; i++){
				State newState = new State(i, s);
				q.add(newState);
			}
		}
		
	
		return bestState.getAction();
	}


}
