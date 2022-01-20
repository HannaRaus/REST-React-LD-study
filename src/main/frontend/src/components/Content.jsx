import React from 'react';

const Content = ({content}) => {
    let mediaType = content.mediaType;
    let media;
    switch (mediaType) {
        case 'Image':
            media = <img key={content.id} className="rounded mx-auto d-block" width="100%" src={content.url}
                         alt="${content.title}"/>
            break;
        case 'Video':
            let embed = content.url.replace('/watch?v=', '/embed/');
            media = <div className="ratio ratio-16x9">
                <iframe src={embed}
                        allowFullScreen/>
            </div>
            break;
    }
    let title;
    if (content.title) {
        title = <h5 className="text-center">{content.title}</h5>;
    }
    let comment;
    if (content.comment) {
        comment = <p className="blockquote text-center">{content.comment}</p>;
    }
    return (
        <div>
            {media}
            {title}
            {comment}
        </div>
    );
};

export default Content;