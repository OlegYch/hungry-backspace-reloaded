/*    */ package org.intellij.HungryBackSpace;
/*    */ 
/*    */ import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ 
/*    */ public class HungryBackSpaceComponent
/*    */   implements ApplicationComponent
/*    */ {
/* 12 */   private boolean mIsStopAtIndent = true;
/*    */   static EditorActionHandler defaultEnterHandler;
/*    */   static EditorActionHandler backspaceHandler;
/*    */ 
/*    */   public String getComponentName()
/*    */   {
/* 18 */     return "HungryBackSpace.Plugin";
/*    */   }
/*    */ 
/*    */   boolean isStopAtIndent() {
/* 22 */     return this.mIsStopAtIndent;
/*    */   }
/*    */ 
/*    */   public void initComponent() {
/* 26 */     ActionManager amgr = ActionManager.getInstance();
/*    */ 
/* 28 */     EditorAction backSpaceAction = (EditorAction)amgr.getAction("EditorBackSpace");
/* 29 */     backspaceHandler = backSpaceAction.getHandler();
/*    */ 
/* 31 */     EditorAction enterAction = (EditorAction)amgr.getAction("EditorEnter");
/* 32 */     defaultEnterHandler = enterAction.getHandler();
/*    */ 
/* 34 */     backSpaceAction.setupHandler(new EditorBackspaceHandler(backspaceHandler));
/*    */   }
/*    */ 
/*    */   public void disposeComponent() {
/*    */   }
/*    */ 
/*    */   static HungryBackSpaceComponent getInstance() {
/* 41 */     return (HungryBackSpaceComponent)ApplicationManager.getApplication().getComponent(HungryBackSpaceComponent.class);
/*    */   }
/*    */ 
/*    */   public void toggleStopAtIndent()
/*    */   {
/* 50 */     this.mIsStopAtIndent = (!this.mIsStopAtIndent);
/*    */   }
/*    */ }

/* Location:           D:\JetBrains\idea\ext_plugins\Hungry_BackSpace_723.jar
 * Qualified Name:     org.intellij.HungryBackSpace.HungryBackSpaceComponent
 * JD-Core Version:    0.6.2
 */