using FlowApp.Core.Entity;
using System;
using System.ComponentModel.DataAnnotations;

namespace FlowApp.Entity.Models
{
    public class Post : IEntity
    {
        public Post()
        {
            this.UpdatedDateTime = DateTime.Now;
            this.Status = true;
        }

        [Key]
        public int Id { get; set; }
        public int UserId { get; set; }
        public DateTime UpdatedDateTime { get; set; }
        public bool Status { get; set; }
    }
}
