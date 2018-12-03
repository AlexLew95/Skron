package com.alexlew.skron.expressions.commenttype;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.alexlew.skron.scopes.ScopeNewComment;
import com.alexlew.skron.types.CommentType;
import org.bukkit.event.Event;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHLabel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Name("About commit")
@Description("Returns a comment type \"commit\"")
@Examples({
        "make new comment about commit:",
        "\t#code"
})
@Since("1.0")

public class ExprAboutCommit extends SimpleExpression<CommentType> {

    static {
        Skript.registerExpression(ExprAboutCommit.class, CommentType.class, ExpressionType.SIMPLE,
                "about [skron] commit %commit%");
    }

    private Expression<GHCommit> commit;

    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        commit = (Expression<GHCommit>) expr[0];
        return true;
    }

    @Override
    protected CommentType[] get( Event e ) {
        ScopeNewComment.lastCommentBuilder.setType(commit.getSingle(e));
        return new CommentType[] {ScopeNewComment.lastCommentBuilder};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends CommentType> getReturnType() {
        return CommentType.class;
    }

    @Override
    public String toString( Event e, boolean debug ) {
        return "about commit";
    }

}
