package Traverse;
import Entity.MethodDef;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YanMing on 2017/9/27.
 */
public class Java8VisitorParser extends Java8BaseVisitor  {
    private ArrayList<MethodDef> methodDefs = new ArrayList<>();


    @Override
    public Object visitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
        //System.out.println(ctx.methodBody().getText());
        //getLeafContext(ctx);
        return visitChildren(ctx);
    }

    @Override
    public Object visitClassDeclaration(Java8Parser.ClassDeclarationContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Object visitNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) {
        /**
         * @Description: 按照 NormalClassDeclaration规则访问AST
         *  @param ctx： NormalClassDeclarationContext
         *
         */
        String className = ctx.Identifier().getText();
        List<Java8Parser.ClassBodyDeclarationContext> bodyDeclarations = ctx.classBody().classBodyDeclaration();
        List<Java8Parser.MethodDeclarationContext> methodDeclarations = new ArrayList<Java8Parser.MethodDeclarationContext>();
        List<Java8Parser.FieldDeclarationContext> fieldDeclarations = new ArrayList<Java8Parser.FieldDeclarationContext>();
        for(Java8Parser.ClassBodyDeclarationContext context:bodyDeclarations)
        {
            Java8Parser.ClassMemberDeclarationContext tmpMember = context.classMemberDeclaration();
            if(tmpMember.getChild(0) instanceof Java8Parser.FieldDeclarationContext)
            {
                fieldDeclarations.add((Java8Parser.FieldDeclarationContext)tmpMember.getChild(0));
            }
            if(tmpMember.getChild(0) instanceof Java8Parser.MethodDeclarationContext)
            {
                methodDeclarations.add((Java8Parser.MethodDeclarationContext) tmpMember.getChild(0));
                ArrayList<Token> tokens = new ArrayList<>();
                Java8Parser.MethodBodyContext methodBodyContext = ((Java8Parser.MethodDeclarationContext) tmpMember.getChild(0)).methodBody();
                StringBuffer stringBuffer = new StringBuffer();
                getLeafContext( ((Java8Parser.MethodDeclarationContext) tmpMember.getChild(0)).methodHeader(),stringBuffer);
                getLeafContext(methodBodyContext,tokens);
                MethodDef methodDef = new MethodDef(tokens,stringBuffer.substring(0,stringBuffer.length()-1),className);
                methodDefs.add(methodDef);

            }
        }
        System.out.println(methodDeclarations.size());
        return visitChildren(ctx);

    }

    //获取某棵树的叶子结点，以token形式存储
    public void getLeafContext(ParseTree ctx,ArrayList<Token> rawStatement){
        if(ctx.getChildCount() == 0)
        {
            if(ctx instanceof TerminalNode)
            {
                Token token = ((TerminalNode) ctx).getSymbol();
                rawStatement.add(token);
            }

        }
        else
        {
            for(int i = 0 ; i <ctx.getChildCount();i++)
            {
                getLeafContext(ctx.getChild(i),rawStatement);
            }
        }
    }

    //获取某棵子树所有节点，以string形式存储
    public void getLeafContext(ParseTree ctx,StringBuffer stringBuffer){
        if(ctx.getChildCount() == 0)
        {
            if(ctx instanceof TerminalNode)
            {
                Token token = ((TerminalNode) ctx).getSymbol();
                stringBuffer.append(token.getText());
                stringBuffer.append(" ");
            }

        }
        else
        {
            for(int i = 0 ; i <ctx.getChildCount();i++)
            {
                getLeafContext(ctx.getChild(i),stringBuffer);
            }
        }
    }

    public ArrayList<MethodDef> getMethodDefs() {
        return methodDefs;
    }
}