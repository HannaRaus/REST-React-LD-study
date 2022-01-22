import React, {useEffect, useState} from "react";
import LessonService from "../services/LessonService";
import Tags from "../components/Tags";
import LessonsTable from "../components/LessonsTable";
import TableHeader from "../components/TableHeader";
import Menu from "../components/Menu";

function LessonsPage() {
    const [tags, setTags] = useState([]);
    const [lessons, setLessons] = useState([]);

    async function loadData() {
        LessonService.getLessons().then(response => {
            console.log(response);
            setTags(response.results.tags);
            setLessons(response.results.lessons);
        })
    }

    useEffect(() => {
        loadData();
    }, []);

    return (
        <div className="Lessons">
            <Menu/>
            <TableHeader lessons={lessons}/>
            <hr/>
            <Tags tags={tags}/>
            <hr/>
            <LessonsTable lessons={lessons}/>
        </div>
    );

}

export default LessonsPage;
