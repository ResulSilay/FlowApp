using FlowApp.Core.Entity;
using System;

namespace FlowApp.Entity.Models
{
    public class ViewPost : IEntity
    {
        public ViewPost()
        {
            this.UpdatedDateTime = DateTime.Now;
            this.Status = true;
        }

        public int PostId { get; set; }
        public int UserId { get; set; }
        public string Username { get; set; }
        public string Description { get; set; }
        public string Img { get; set; }
        public string Picture { get; set; }
        public double RateValue { get; set; }
        public DateTime UpdatedDateTime { get; set; }
        public DateTime CreatedDateTime { get; set; }
        public bool Status { get; set; }
    }
}
