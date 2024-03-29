﻿using FlowApp.Core.Entity;
using System;
using System.ComponentModel.DataAnnotations;

namespace FlowApp.Entity.Models
{
    public class Rating : IEntity
    {
        public Rating()
        {
            this.UpdatedDateTime = DateTime.Now;
            this.Status = true;
        }

        [Key]
        public int Id { get; set; }
        public int PostId { get; set; }
        public int UserId { get; set; }
        public double Rate { get; set; }
        public DateTime UpdatedDateTime { get; set; }
        public bool Status { get; set; }
    }
}
