//<!-- script for sending id in modal "Delete anyway" button --!>
$('#deleteModal').on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget) // Button that triggered the modal
    let lessonId = button.data('lesson-id') // Extract info from data-* attributes
    let lessonTitle = button.data('lesson-title')

    let href = '/lessons/delete?id=' + lessonId // link forming

    let modal = $(this)
    modal.find('.modal-body p').text('Are you sure you want to delete lesson with title - ' + lessonTitle + '?')
    $('.btn-danger', this).attr('href', href)
})