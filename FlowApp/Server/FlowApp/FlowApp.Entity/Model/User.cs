using FlowApp.Core.Entity;
using System;
using System.ComponentModel.DataAnnotations;

namespace FlowApp.Entity.Models
{
    public class User : IEntity
    {
        public User()
        {
            this.UpdatedDateTime = DateTime.Now;
            this.Status = true;
        }

        [Key]
        public int Id { get; set; }
        public string Img { get; set; }
        public string Username { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
        public string Phone { get; set; }
        public string Address { get; set; }
        public DateTime UpdatedDateTime { get; set; }
        public bool Status { get; set; }
    }
}
