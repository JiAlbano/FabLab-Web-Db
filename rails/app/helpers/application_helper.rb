module ApplicationHelper
  def render_modal(id: "", title: "", body: "", form_model: "", footer: "")
    render(partial: "layouts/modal", locals: { id: id, title: title, body: body, form_model: form_model, footer: footer })
  end
end
