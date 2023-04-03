package com.Me.ShiftBoard.Querydsl;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.Me.ShiftBoard.Models.DatabaseSequence;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDatabaseSequence is a Querydsl query type for DatabaseSequence
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDatabaseSequence extends EntityPathBase<DatabaseSequence> {

    private static final long serialVersionUID = -502867486L;

    public static final QDatabaseSequence databaseSequence = new QDatabaseSequence("databaseSequence");

    public final StringPath id = createString("id");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QDatabaseSequence(String variable) {
        super(DatabaseSequence.class, forVariable(variable));
    }

    public QDatabaseSequence(Path<? extends DatabaseSequence> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDatabaseSequence(PathMetadata metadata) {
        super(DatabaseSequence.class, metadata);
    }

}

