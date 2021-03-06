package com.olyno.skron.skript.expressions.repository.repositorybuilder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.olyno.skron.util.classes.RepositoryBuilder;
import org.bukkit.event.Event;

@Name("Squash Merge state of Repository Builder")
@Description("Returns squash merge state of a repository builder. Can be set."
        + "The object type must be a repository builder.\n"
        + "The value must be a Boolean (true or false).\n"
        + "The squash merge state will accept or not squashes in the repository.\n\n"
        + "Default: true"
)
@Examples({
        "on skript load:\n" +
                "\tlogin to github account \"olyno\" with oauth \"my secret oauth\"\n" +
                "\n" +
                "command create:\n" +
                "\ttrigger:\n" +
                "\t\tmake new repository named \"test\":\n" +
                "\t\t\tset squash merge state of repository to true\n" +
                "\t\tcreate repository \"test\"\n" +
                "\t\tsend \"Repository created!\""
})
@Since("1.0.0")

public class ExprSquashMergeOfRepositoryBuilder extends SimplePropertyExpression<RepositoryBuilder, Boolean> {

    static {
        register(ExprSquashMergeOfRepositoryBuilder.class, Boolean.class,
                "squash merge state", "repositorybuilder"
        );
    }

    @Override
    public Boolean convert(RepositoryBuilder repository) {
        return repository.allowSquashMerge();
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        switch (mode) {
            case SET:
            case DELETE:
            case RESET:
                return new Class[]{Boolean.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        for (Object o : getExpr().getArray(e)) {
            RepositoryBuilder repository = (RepositoryBuilder) o;
            switch (mode) {
                case SET:
                    repository.allowSquashMerge((Boolean) delta[0]);
                    break;
                case RESET:
                case DELETE:
                    repository.allowSquashMerge(true);
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "squash merge state";
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }
}
