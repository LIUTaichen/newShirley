import { Component, OnInit, Input } from '@angular/core';
import { Niggle } from '../../../entities/niggle';

@Component({
  selector: 'jhi-over-due-report',
  templateUrl: './over-due-report.component.html',
  styleUrls: ['./over-due-report.component.css']
})
export class OverDueReportComponent implements OnInit {

  @Input() niggles: Niggle[];
  displayedColumns = ['priority', 'total', 'overdue', 'percentage'];
  dataSource = ELEMENT_DATA;

  constructor() { }

  ngOnInit() {

  }

}
export interface Element {
  priority: string;
  total: number;
  overdue: number;
  percentage: number;
}

const ELEMENT_DATA: Element[] = [
  {priority: 'HIGH', total: 80 , overdue: 65, percentage: 0.81},
  {priority: 'MEDIUM', total: 78 , overdue: 46, percentage: 0.59},
  {priority: 'LOW', total: 30 , overdue: 13, percentage: 0.43},
  {priority: 'TOTAL', total: 189 , overdue: 121, percentage: 0.64},
];
