/*    */ package org.intellij.HungryBackSpace;
/*    */ 
/*    */ import com.intellij.openapi.actionSystem.AnActionEvent;
/*    */ import com.intellij.openapi.actionSystem.ToggleAction;
/*    */ 
/*    */ public class HungryBackSpaceStopAtIndentAction extends ToggleAction
/*    */ {
/*    */   private static HungryBackSpaceComponent getHungryBackSpaceComponent()
/*    */   {
/* 17 */     return HungryBackSpaceComponent.getInstance();
/*    */   }
/*    */ 
/*    */   public boolean isSelected(AnActionEvent e) {
/* 21 */     return getHungryBackSpaceComponent().isStopAtIndent();
/*    */   }
/*    */ 
/*    */   public void setSelected(AnActionEvent e, boolean state)
/*    */   {
/* 28 */     getHungryBackSpaceComponent().toggleStopAtIndent();
/*    */   }
/*    */ }

/* Location:           D:\JetBrains\idea\ext_plugins\Hungry_BackSpace_723.jar
 * Qualified Name:     org.intellij.HungryBackSpace.HungryBackSpaceStopAtIndentAction
 * JD-Core Version:    0.6.2
 */