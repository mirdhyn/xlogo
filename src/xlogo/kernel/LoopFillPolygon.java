package xlogo.kernel;

import java.math.BigDecimal;

public class LoopFillPolygon extends LoopProperties {
	/**
	 * The super constructor for Fill Polygon Loops
	 * @param instr The instruction to execute each loop
	 */
	
		LoopFillPolygon(){
			super(BigDecimal.ONE,BigDecimal.ONE,BigDecimal.ONE,"");
		}
		protected boolean isForEver(){
			return false;
		}
		protected boolean isFillPolygon(){
			return true;
		}
}
