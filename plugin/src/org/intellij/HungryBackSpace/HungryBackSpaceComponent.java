/*    */ package org.intellij.HungryBackSpace;
/*    */
/*    */

import com.intellij.ide.ApplicationInitializedListener;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.editor.actionSystem.EditorAction;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class HungryBackSpaceComponent implements ApplicationInitializedListener
/*    */ {
            private static String stopAtIndentName = "HungryBackspaceReloaded.StopAtIndent";
            private static boolean defaultStopAtIndent = true;

    /*    */   public static boolean isStopAtIndent() {
/* 22 */
            return PropertiesComponent.getInstance().getBoolean(stopAtIndentName, defaultStopAtIndent);
/*    */   }
/*    */
    @Override
    public void componentsInitialized() {
        /* 26 */     ActionManager amgr = ActionManager.getInstance();
        /* 28 */     EditorAction backSpaceAction = (EditorAction)amgr.getAction("EditorBackSpace");
        /* 31 */     EditorAction enterAction = (EditorAction)amgr.getAction("EditorEnter");
        /* 34 */     backSpaceAction.setupHandler(new EditorBackspaceHandler(backSpaceAction.getHandler(), enterAction.getHandler()));
    }
/*    */   public static void setStopAtIndent(boolean value)
/*    */   {
/* 50 */
                PropertiesComponent.getInstance().setValue(stopAtIndentName, value, defaultStopAtIndent);
/*    */   }
/*    */ }

/* Location:           D:\JetBrains\idea\ext_plugins\Hungry_BackSpace_723.jar
 * Qualified Name:     org.intellij.HungryBackSpace.HungryBackSpaceComponent
 * JD-Core Version:    0.6.2
 */