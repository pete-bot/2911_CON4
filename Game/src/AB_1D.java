import java.util.PriorityQueue;


public class AB_1D implements MechanicalTurkInterface{
	
	@Override
	public Action getTurkMove(BackendBoard backendBoard) {
		PriorityQueue<State> q = new PriorityQueue<State>();
		
		State s = new State(backendBoard);
		
		for (int i=0; i < 7; i++){
			State newState = new State(i, s);
			q.add(newState);
		}
		
		s = q.poll();
	
		return s.getAction();
	}


}
