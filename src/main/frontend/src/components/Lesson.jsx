import React from 'react';

const Lesson = ({lesson}) => {
    let author;
    if (lesson.author) {
        author = <footer className="blockquote-footer">Author {lesson.author.name}</footer>;
    }
    return (
        <div>
            <div className="blockquote text-right">
                <footer>{lesson.creationDate}</footer>
            </div>
            <div className="text-center">
                <h1>{lesson.title}</h1>
            </div>
            <blockquote className="blockquote text-center">
                <p className="mb-0">{lesson.description}</p>
                <p/>
                {author}
            </blockquote>
        </div>
    );
};

export default Lesson;