/*     */ package org.intellij.HungryBackSpace;
/*     */ 
/*     */ import com.intellij.openapi.actionSystem.DataContext;
/*     */ import com.intellij.openapi.application.Application;
/*     */ import com.intellij.openapi.application.ApplicationManager;
/*     */ import com.intellij.openapi.editor.CaretModel;
/*     */ import com.intellij.openapi.editor.Document;
/*     */ import com.intellij.openapi.editor.Editor;
/*     */ import com.intellij.openapi.editor.SelectionModel;
/*     */ import com.intellij.openapi.editor.VisualPosition;
/*     */ import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
/*     */ import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
/*     */ import com.intellij.openapi.util.TextRange;
/*     */ 
/*     */ public class EditorBackspaceHandler extends EditorWriteActionHandler
/*     */ {
/*     */   protected EditorActionHandler mOrigHandler;
/*     */   protected Application mApplication;
/*     */ 
/*     */   public EditorBackspaceHandler(EditorActionHandler origHandler)
/*     */   {
/*  27 */     this.mOrigHandler = origHandler;
/*  28 */     this.mApplication = ApplicationManager.getApplication();
/*     */   }
/*     */ 
/*     */   public void executeWriteAction(Editor editor, DataContext dataContext)
/*     */   {
/*  35 */     if (editor == null)
/*     */     {
/*  37 */       this.mOrigHandler.execute(editor, dataContext);
/*     */     }
/*     */     else {
/*  40 */       Document document = editor.getDocument();
/*  41 */       if (!document.isWritable()) {
/*  42 */         this.mOrigHandler.execute(editor, dataContext);
/*  43 */         return;
/*     */       }
/*  45 */       CaretModel caretModel = editor.getCaretModel();
/*  46 */       SelectionModel selectionModel = editor.getSelectionModel();
/*     */ 
/*  49 */       if (selectionModel.hasSelection())
/*     */       {
/*  51 */         this.mOrigHandler.execute(editor, dataContext);
/*  52 */         return;
/*     */       }
/*     */ 
/*  55 */       char[] text = document.getChars();
/*  56 */       VisualPosition visualPosition = caretModel.getVisualPosition();
/*  57 */       int vline = visualPosition.line;
/*  58 */       int vcolumn = visualPosition.column;
/*  59 */       int endOffset = caretModel.getOffset();
/*  60 */       VisualPosition offsetVisualPosition = editor.offsetToVisualPosition(endOffset);
/*  61 */       int ocolumn = offsetVisualPosition.column;
/*  62 */       int oline = offsetVisualPosition.line;
/*     */ 
/*  66 */       boolean containsWS = false;
/*  67 */       if (vcolumn > ocolumn) {
/*  68 */         containsWS = true;
/*     */       }
/*     */ 
/*  96 */       int startOffset = 0;
/*  97 */       for (int i = endOffset; i > 1; i--)
/*     */       {
/*  99 */         if (!Character.isWhitespace(text[(i - 1)])) {
/* 100 */           if (containsWS) {
/* 101 */             startOffset = i; break;
/*     */           }
/* 103 */           startOffset = i - 1;
/*     */ 
/* 105 */           break;
/*     */         }
/* 107 */         containsWS = true;
/*     */       }
/*     */ 
/* 111 */       if (!containsWS)
/*     */       {
/* 115 */         this.mOrigHandler.execute(editor, dataContext);
/* 116 */         return;
/*     */       }
/*     */ 
/* 122 */       if (HungryBackSpaceComponent.getInstance().isStopAtIndent())
/*     */       {
/* 124 */         VisualPosition startVP = editor.offsetToVisualPosition(startOffset);
/* 125 */         VisualPosition endVP = editor.offsetToVisualPosition(endOffset);
/* 126 */         TextRange lineRange = GetLineRange(editor, endVP.line);
/* 127 */         boolean hasTextBefore = false;
/* 128 */         boolean hasTextAfter = false;
/* 129 */         boolean isLineBlank = true;
/* 130 */         for (int i = lineRange.getStartOffset(); i <= lineRange.getEndOffset(); i++) {
/* 131 */           if (!Character.isWhitespace(text[i])) {
/* 132 */             isLineBlank = false;
/* 133 */             if (i < endOffset)
/* 134 */               hasTextBefore = true;
/* 135 */             else if (i >= endOffset) {
/* 136 */               hasTextAfter = true;
/*     */             }
/* 138 */             isLineBlank = false;
/*     */           }
/*     */         }
/* 141 */         if (hasTextBefore)
/*     */         {
/* 144 */           caretModel.moveToOffset(startOffset);
/* 145 */           document.deleteString(startOffset, endOffset);
/*     */         }
/*     */         else
/*     */         {
/* 149 */           caretModel.moveToOffset(startOffset);
/* 150 */           document.deleteString(startOffset, endOffset);
/*     */ 
/* 152 */           HungryBackSpaceComponent.defaultEnterHandler.execute(editor, dataContext);
/* 153 */           if (endVP.line - startVP.line <= 1)
/*     */           {
/* 159 */             int indentOffset = caretModel.getOffset();
/* 160 */             VisualPosition indentVP = editor.offsetToVisualPosition(indentOffset);
/* 161 */             if (endVP.column <= indentVP.column)
/*     */             {
/* 165 */               caretModel.moveToOffset(startOffset);
/* 166 */               document.deleteString(startOffset, indentOffset);
/*     */             }
/*     */           }
/*     */         }
/* 170 */         return;
/*     */       }
/*     */ 
/* 174 */       caretModel.moveToOffset(startOffset);
/* 175 */       document.deleteString(startOffset, endOffset);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int GetStartOfLineOffset(Editor editor, int line)
/*     */   {
/* 184 */     VisualPosition vp = new VisualPosition(line, 0);
/* 185 */     return editor.logicalPositionToOffset(editor.visualToLogicalPosition(vp));
/*     */   }
/*     */ 
/*     */   public static int GetEndOfLineOffset(Editor editor, int line)
/*     */   {
/* 192 */     return GetStartOfLineOffset(editor, line + 1) - 1;
/*     */   }
/*     */ 
/*     */   public static TextRange GetLineRange(Editor editor, int line)
/*     */   {
/* 199 */     int startOffset = GetStartOfLineOffset(editor, line);
/* 200 */     int endOffset = GetEndOfLineOffset(editor, line);
/* 201 */     return new TextRange(startOffset, endOffset);
/*     */   }
/*     */ }

/* Location:           D:\JetBrains\idea\ext_plugins\Hungry_BackSpace_723.jar
 * Qualified Name:     org.intellij.HungryBackSpace.EditorBackspaceHandler
 * JD-Core Version:    0.6.2
 */