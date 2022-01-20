import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import LessonService from "../services/LessonService";
import Content from "../components/Content";
import Lesson from "../components/Lesson";


const LessonPage = () => {
    const params = useParams();
    const [lesson, setLesson] = useState({});
    const [contents, setContents] = useState([]);
    const [tags, setTags] = useState([]);

    async function loadData(id) {
        await LessonService.getLesson(id).then(response => {
            console.log(response);
            setLesson(response.results.lesson);
            setContents(response.results.lesson.contents);
            setTags(response.results.lesson.tags);
        })
    }

    useEffect(() => {
        loadData(params.id);
    }, []);

    let tagsList;
    if (tags) {
        tagsList = tags.map((tag) => {
            return (
                <label key={tag.id} className="btn btn-outline-secondary">${tag.label}</label>
            )
        })
    }
    return (
        <div className="container">
            <Lesson lesson={lesson}/>
            {contents.map((content) => {
                    return (
                        <Content content={content} key={content.id}/>
                    )
                }
            )}
            {tagsList}
        </div>
    )
        ;

};

export default LessonPage;